package com.stomat.services.booking;

import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ExtraSchedule;
import com.stomat.domain.schedule.WeekSchedule;
import com.stomat.repository.schedule.ExtraScheduleRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static com.stomat.domain.schedule.ExtraScheduleTypeEnum.EXCLUDE;
import static com.stomat.domain.schedule.ExtraScheduleTypeEnum.INCLUDE;

@RunWith(SpringRunner.class)
public class FreeTimeCalculationServiceTest {

    @MockBean
    private ExtraScheduleRepository extraScheduleRepository;

    @Before
    public void setUp() {
        freeTimeCalculationService = new FreeTimeCalculationService(extraScheduleRepository);
    }

    private FreeTimeCalculationService freeTimeCalculationService;

    @Test
    public void testWeekScheduleFreeTimes() {
        //given
        Doctor doctor = new Doctor();

        Set<WeekSchedule> weekScheduleSet = Set.of(
                new WeekSchedule(doctor, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 30)),
                new WeekSchedule(doctor, DayOfWeek.MONDAY, LocalTime.of(12, 10), LocalTime.of(16, 0)),
                new WeekSchedule(doctor, DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(17, 0))
        );

        doctor.setWeekSchedules(weekScheduleSet);

        LocalDate nextMonday = nextMonday();

        //when
        var ar = freeTimeCalculationService.weekScheduleRanges(doctor, nextMonday, nextMonday.plusWeeks(1));


        // build expected result
        var builder = ImmutableRangeSet.builder();
        builder.add(Range.closedOpen(nextMonday.atTime(8, 0), nextMonday.atTime(10, 30)));
        builder.add(Range.closedOpen(nextMonday.atTime(12, 10), nextMonday.atTime(16, 0)));
        var nextTue = nextMonday.plusDays(1);
        builder.add(Range.closedOpen(nextTue.atTime(8, 0), nextTue.atTime(17, 0)));

        //then
        TestCase.assertEquals(builder.build(), ar);
    }

    @Test
    public void testExtraIncludeScheduleFreeTimes() {
        //given
        Doctor doctor = new Doctor();

        LocalDate nextMonday = nextMonday();

        Set<ExtraSchedule> extraScheduleSet = Set.of(
                new ExtraSchedule(doctor, nextMonday.atTime(8, 0), nextMonday.atTime(10, 30), INCLUDE, false),
                new ExtraSchedule(doctor, nextMonday.atTime(12, 10), nextMonday.atTime(16, 0), INCLUDE, false)
        );

        Mockito.when(extraScheduleRepository.findAllByDoctorEqualsAndFromDateAfterAndFromDateBeforeAndType(
                doctor, nextMonday.atStartOfDay(), nextMonday.plusWeeks(1).atStartOfDay(), INCLUDE))
                .thenReturn(extraScheduleSet);

        //when
        var ar = freeTimeCalculationService.extraScheduleIncludeRanges(doctor, nextMonday, nextMonday.plusWeeks(1));


        // build expected result
        var builder = ImmutableRangeSet.builder();
        builder.add(Range.closedOpen(nextMonday.atTime(8, 0), nextMonday.atTime(10, 30)));
        builder.add(Range.closedOpen(nextMonday.atTime(12, 10), nextMonday.atTime(16, 0)));

        //then
        TestCase.assertEquals(builder.build(), ar);
    }

    @Test
    public void testExtraExcludeScheduleFreeTimes() {
        //given
        Doctor doctor = new Doctor();

        LocalDate nextMonday = nextMonday();

        LocalDate nextTue = nextMonday.plusDays(1);
        Set<ExtraSchedule> extraScheduleSet = Set.of(
                new ExtraSchedule(doctor, nextMonday.atTime(8, 0), nextMonday.atTime(10, 30), EXCLUDE, false),
                new ExtraSchedule(doctor, nextMonday.atTime(12, 10), nextMonday.atTime(16, 0), EXCLUDE, false),
                new ExtraSchedule(doctor, nextTue.atTime(0, 0), null, EXCLUDE, true)
        );

        LocalDate secondMondayAfterNow = nextMonday.plusWeeks(1);
        Mockito.when(extraScheduleRepository.findAllByDoctorEqualsAndFromDateAfterAndFromDateBeforeAndType(
                doctor, nextMonday.atStartOfDay(), secondMondayAfterNow.atStartOfDay(), EXCLUDE))
                .thenReturn(extraScheduleSet);

        //when
        var ar = freeTimeCalculationService.extraScheduleExcludeRanges(doctor, nextMonday, secondMondayAfterNow);


        // build expected result
        var builder = ImmutableRangeSet.builder();
        builder.add(Range.closedOpen(nextMonday.atTime(8, 0), nextMonday.atTime(10, 30)));
        builder.add(Range.closedOpen(nextMonday.atTime(12, 10), nextMonday.atTime(16, 0)));
        builder.add(Range.closedOpen(nextTue.atTime(0, 0), nextTue.plusDays(1).atTime(0, 0)));

        //then
        TestCase.assertEquals(builder.build(), ar);
    }

    @Test
    public void testCalculateFreeTimes() {
        //given
        Doctor doctor = new Doctor();

        //week schedule
        Set<WeekSchedule> weekScheduleSet = Set.of(
                new WeekSchedule(doctor, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)),
                new WeekSchedule(doctor, DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)),
                new WeekSchedule(doctor, DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(17, 0))
        );

        doctor.setWeekSchedules(weekScheduleSet);

        LocalDate nextMonday = nextMonday();

        //extra schedule INCLUDE
        Set<ExtraSchedule> extraScheduleIncludeSet = Set.of(
                new ExtraSchedule(doctor, nextMonday.atTime(18, 0), nextMonday.atTime(19, 30), INCLUDE, false)
        );

        Mockito.when(extraScheduleRepository.findAllByDoctorEqualsAndFromDateAfterAndFromDateBeforeAndType(
                doctor, nextMonday.atStartOfDay(), nextMonday.plusWeeks(1).atStartOfDay(), INCLUDE))
                .thenReturn(extraScheduleIncludeSet);

        //extra schedule EXCLUDE
        LocalDate nextTue = nextMonday.plusDays(1);
        Set<ExtraSchedule> extraScheduleSet = Set.of(
                new ExtraSchedule(doctor, nextTue.atTime(12, 30), nextTue.atTime(14, 0), EXCLUDE, false),
                new ExtraSchedule(doctor, nextTue.plusDays(1).atStartOfDay(), null, EXCLUDE, true)
        );

        LocalDate secondMondayAfterNow = nextMonday.plusWeeks(1);
        Mockito.when(extraScheduleRepository.findAllByDoctorEqualsAndFromDateAfterAndFromDateBeforeAndType(
                doctor, nextMonday.atStartOfDay(), secondMondayAfterNow.atStartOfDay(), EXCLUDE))
                .thenReturn(extraScheduleSet);

        //when
        var ar = freeTimeCalculationService.calculateFreeTimes(doctor, nextMonday, secondMondayAfterNow);


        // build expected result
        var builder = ImmutableRangeSet.builder();
        builder.add(Range.closedOpen(nextMonday.atTime(8, 0), nextMonday.atTime(17, 0)));
        builder.add(Range.closedOpen(nextMonday.atTime(18, 0), nextMonday.atTime(19, 30)));
        builder.add(Range.closedOpen(nextTue.atTime(8, 0), nextTue.atTime(12, 30)));
        builder.add(Range.closedOpen(nextTue.atTime(14, 0), nextTue.atTime(17, 0)));

        //then
        TestCase.assertEquals(builder.build(), ar);
    }

    @Test
    public void testIntersectionMinTime() {
        //given
        Doctor doctor = new Doctor();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        doctor.setWeekSchedules(Set.of(
                new WeekSchedule(doctor, yesterday.getDayOfWeek(), yesterday.toLocalTime().withHour(8), yesterday.toLocalTime().withHour(17))
        ));

        //when
        LocalDate dayBeforeYesterday = yesterday.toLocalDate().minusDays(1);
        LocalDate today = LocalDateTime.now().toLocalDate();
        var ar = freeTimeCalculationService.calculateFreeTimes(doctor, dayBeforeYesterday, today);

        //then
        TestCase.assertEquals(ImmutableRangeSet.of(), ar);
    }

    private LocalDate nextMonday() {
        LocalDate now = LocalDate.now();
        var currentWeekDayValue = now.getDayOfWeek().getValue();
        var diffNextWeekInDays = 8 - currentWeekDayValue;

        return now.plusDays(diffNextWeekInDays);
    }
}
