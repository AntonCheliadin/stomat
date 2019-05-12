package com.stomat.transfer.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stomat.domain.schedule.ExtraScheduleTypeEnum;

import java.time.LocalDateTime;

public class ExtraScheduleDto {

    private Long id;

    private Long doctor;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime toDate;

    private ExtraScheduleTypeEnum type;

    private boolean allDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(Long doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }


    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public ExtraScheduleTypeEnum getType() {
        return type;
    }

    public void setType(ExtraScheduleTypeEnum type) {
        this.type = type;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }
}
