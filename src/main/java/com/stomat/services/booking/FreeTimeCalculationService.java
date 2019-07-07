package com.stomat.services.booking;

import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.stomat.domain.booking.Booking;
import com.stomat.domain.booking.Reason;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ExtraScheduleTypeEnum;
import com.stomat.domain.schedule.WeekSchedule;
import com.stomat.repository.booking.BookingRepository;
import com.stomat.repository.schedule.ExtraScheduleRepository;
import com.stomat.transfer.booking.FreeTimeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableRangeSet.unionOf;

@SuppressWarnings("UnstableApiUsage")
@Service
public class FreeTimeCalculationService {

    private ExtraScheduleRepository extraScheduleRepository;
    private BookingRepository bookingRepository;

    @Value("${booking.round.to.minute}")
    public Integer roundToMinute;

    public FreeTimeCalculationService(ExtraScheduleRepository extraScheduleRepository, BookingRepository bookingRepository) {
        this.extraScheduleRepository = extraScheduleRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<FreeTimeDto> collectFreeTimes(Doctor doctor, Reason reason, LocalDate start, LocalDate end) {
        ImmutableRangeSet<LocalDateTime> freeTimes = calculateFreeTimes(doctor, start, end);

        return buildFreeTimeDto(freeTimes, reason);
    }

    ImmutableRangeSet<LocalDateTime> calculateFreeTimes(Doctor doctor, LocalDate start, LocalDate end) {
        return weekScheduleRanges(doctor, start, end)
                .union(extraScheduleIncludeRanges(doctor, start, end))
                .difference(extraScheduleExcludeRanges(doctor, start, end))
                .difference(bookingsRangeSet(doctor, start, end))
                .intersection(minBookingTime(doctor));
    }

    List<FreeTimeDto> buildFreeTimeDto(ImmutableRangeSet<LocalDateTime> freeTimes, Reason reason) {

        var freeTimeDtos = new ArrayList<FreeTimeDto>();

        freeTimes.asRanges().forEach(range -> {
            var startEvent = range.lowerEndpoint();
            var endEvent = startEvent.plusMinutes(reason.getDuration());

            while (!endEvent.isAfter(range.upperEndpoint())) {

                freeTimeDtos.add(new FreeTimeDto(startEvent, endEvent));

                startEvent = endEvent;
                endEvent = startEvent.plusMinutes(reason.getDuration());
            }
        });

        return freeTimeDtos;
    }

    private ImmutableRangeSet<LocalDateTime> minBookingTime(Doctor doctor) {
        LocalDateTime minTime = LocalDateTime.now();
        minTime = minTime.withSecond(0);
        minTime = minTime.withNano(0);

        if (doctor.getMinBookingTime() != null) {
            minTime = minTime.plusMinutes(doctor.getMinBookingTime());
        }

        LocalDateTime roundedMinTime;

        int roundedMinutes = minTime.getMinute() + (roundToMinute - (minTime.getMinute() % roundToMinute));
        if (roundedMinutes != 60) {
            roundedMinTime = minTime.withMinute(roundedMinutes);
        } else {
            roundedMinTime = minTime.withMinute(0);
            roundedMinTime = roundedMinTime.plusHours(1);
        }

        return ImmutableRangeSet.of(Range.atLeast(roundedMinTime));
    }

    ImmutableRangeSet<LocalDateTime> weekScheduleRanges(Doctor doctor, LocalDate start, LocalDate end) {
        Set<WeekSchedule> weekSchedules = doctor.getWeekSchedules();

        if (weekSchedules == null || weekSchedules.isEmpty()) {
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

    private ImmutableRangeSet<LocalDateTime> bookingsRangeSet(Doctor doctor, LocalDate start, LocalDate end) {

        List<Booking> bookings = bookingRepository.findAllByDoctorEqualsAndStartDateBetween(
                doctor, start.atStartOfDay(), end.atStartOfDay());

        var builder = ImmutableRangeSet.<LocalDateTime>builder();
        bookings.forEach(it -> {
            builder.add(Range.closedOpen(it.getStartDate(), it.getEndDate()));
        });

        return builder.build();
    }
}
