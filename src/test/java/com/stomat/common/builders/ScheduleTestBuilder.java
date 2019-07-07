package com.stomat.common.builders;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.WeekSchedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class ScheduleTestBuilder {

    public static void addWeekScheduleToDoctor(DayOfWeek dayOfWeek, LocalTime from, LocalTime to, Doctor doctor) {
        WeekSchedule weekSchedule = new WeekSchedule(doctor, dayOfWeek, from, to);

        Set<WeekSchedule> schedules = doctor.getWeekSchedules();
        if (schedules == null) {
            schedules = new HashSet<>();
        }
        schedules.add(weekSchedule);
        doctor.setWeekSchedules(schedules);
    }
}
