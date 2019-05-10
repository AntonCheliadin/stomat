package com.stomat.transfer.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.AdditionalTimeTypeEnum;

import java.time.LocalDateTime;

public class ExtraScheduleDto {

    private Long id;

    private Long doctor;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private AdditionalTimeTypeEnum type;

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

    public AdditionalTimeTypeEnum getType() {
        return type;
    }

    public void setType(AdditionalTimeTypeEnum type) {
        this.type = type;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }
}
