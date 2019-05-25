package com.stomat.services.booking;

import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.stomat.domain.booking.Booking;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ExtraScheduleTypeEnum;
import com.stomat.domain.schedule.WeekSchedule;
import com.stomat.repository.booking.BookingRepository;
import com.stomat.repository.schedule.ExtraScheduleRepository;
import com.stomat.transfer.booking.FreeTimeDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableRangeSet.unionOf;

@Service
public class FreeTimeCalculationService {

    static int BOOKING_REASON_LENGTH = 90;//todo: reason domain

    private ExtraScheduleRepository extraScheduleRepository;
    private BookingRepository bookingRepository;

    public FreeTimeCalculationService(ExtraScheduleRepository extraScheduleRepository, BookingRepository bookingRepository) {
        this.extraScheduleRepository = extraScheduleRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<FreeTimeDto> collectFreeTimes(Doctor doctor, LocalDate start, LocalDate end) {
        ImmutableRangeSet<LocalDateTime> freeTimes = calculateFreeTimes(doctor, start, end);

        return buildFreeTimeDto(freeTimes);
    }

    ImmutableRangeSet<LocalDateTime> calculateFreeTimes(Doctor doctor, LocalDate start, LocalDate end) {

        var freeTimes = weekScheduleRanges(doctor, start, end)
                .union(extraScheduleIncludeRanges(doctor, start, end))
                .difference(extraScheduleExcludeRanges(doctor, start, end))
                .difference(bookingsRangeSet(doctor, start, end))
                .intersection(minBookingTime());

        return freeTimes;
    }

    List<FreeTimeDto> buildFreeTimeDto(ImmutableRangeSet<LocalDateTime> freeTimes) {

        var freeTimeDtos = new ArrayList<FreeTimeDto>();

        freeTimes.asRanges().forEach(range -> {
            var startEvent = range.lowerEndpoint();
            var endEvent = startEvent.plusMinutes(BOOKING_REASON_LENGTH);

            while (!endEvent.isAfter(range.upperEndpoint())) {

                freeTimeDtos.add(new FreeTimeDto(startEvent, endEvent));

                startEvent = endEvent;
                endEvent = startEvent.plusMinutes(BOOKING_REASON_LENGTH);
            }
        });

        return freeTimeDtos;
    }

    ImmutableRangeSet<LocalDateTime> minBookingTime() {
        LocalDateTime minTime = LocalDateTime.now().plusHours(2).withMinute(0);//todo: to doctor field

        return ImmutableRangeSet.of(Range.atLeast(minTime));
    }

    ImmutableRangeSet<LocalDateTime> weekScheduleRanges(Doctor doctor, LocalDate start, LocalDate end) {
        Set<WeekSchedule> weekSchedules = doctor.getWeekSchedules();

        if (weekSchedules.isEmpty()) {
            return ImmutableRangeSet.of();
        }

        var builder = ImmutableRangeSet.<LocalDateTime>builder();

        start.datesUntil(end).forEach(day -> {
            final LocalDate finalDay = day;

            weekSchedules
                    .stream()
                    .filter(it ->
                            it.getDayOfWeek() == finalDay.getDayOfWeek())
                    .forEach(it -> {
                        var timeStart = finalDay.atTime(it.getTimeFrom());
                        var timeEnd = finalDay.atTime(it.getTimeTo());

                        builder.add(Range.closedOpen(timeStart, timeEnd));
                    });
        });

        return builder.build();
    }

    ImmutableRangeSet<LocalDateTime> extraScheduleIncludeRanges(Doctor doctor, LocalDate start, LocalDate end) {
        var extraSchedules = extraScheduleRepository.findAllByDoctorEqualsAndFromDateAfterAndFromDateBeforeAndType(
                doctor, start.atStartOfDay(), end.atStartOfDay(), ExtraScheduleTypeEnum.INCLUDE);

        var ranges = extraSchedules.stream()
                .map(it -> Range.closedOpen(it.getFromDate(), it.getToDate()))
                .collect(Collectors.toList());

        return unionOf(ranges);
    }

    ImmutableRangeSet<LocalDateTime> extraScheduleExcludeRanges(Doctor doctor, LocalDate start, LocalDate end) {
        var extraSchedules = extraScheduleRepository.findAllByDoctorEqualsAndFromDateAfterAndFromDateBeforeAndType(
                doctor, start.atStartOfDay(), end.atStartOfDay(), ExtraScheduleTypeEnum.EXCLUDE);

        var ranges = extraSchedules.stream()
                .map(it -> {
                    if (it.isAllDay()) {
                        LocalDateTime startOfDay = it.getFromDate().toLocalDate().atStartOfDay();
                        return Range.closedOpen(startOfDay, startOfDay.plusDays(1));
                    } else {
                        return Range.closedOpen(it.getFromDate(), it.getToDate());
                    }
                })
                .collect(Collectors.toList());

        return unionOf(ranges);
    }

    ImmutableRangeSet<LocalDateTime> bookingsRangeSet(Doctor doctor, LocalDate start, LocalDate end) {

        List<Booking> bookings = bookingRepository.findAllByDoctorEqualsAndStartDateBetween(
                doctor, start.atStartOfDay(), end.atStartOfDay());

        var builder = ImmutableRangeSet.<LocalDateTime>builder();
        bookings.forEach(it -> {
            builder.add(Range.closedOpen(it.getStartDate(), it.getEndDate()));
        });

        return builder.build();
    }
}
