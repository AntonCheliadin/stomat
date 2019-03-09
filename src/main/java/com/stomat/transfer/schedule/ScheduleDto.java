package com.stomat.transfer.schedule;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.stomat.constants.DateFormat.API_DATE_TIME;

public class ScheduleDto {

    /**
     * SUNDAY = 1
     * ...
     */
    @Min(1)
    @Max(7)
    private int dayOfWeek;


    @NotNull
    @DateTimeFormat(pattern = API_DATE_TIME)
    private Date timeFrom;

    @NotNull
    @DateTimeFormat(pattern = API_DATE_TIME)
    private Date timeTo;

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }
}
