package com.stomat.transfer.schedule;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class ScheduleDto {

    /**
     * SUNDAY = 1
     * ...
     */
    @Min(1)
    @Max(7)
    private int dayOfWeek;

    @NotNull
    private long doctor;

    @NotNull
    private LocalTime timeFrom;

    @NotNull
    private LocalTime timeTo;

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    public long getDoctor() {
        return doctor;
    }

    public void setDoctor(long doctor) {
        this.doctor = doctor;
    }
}
