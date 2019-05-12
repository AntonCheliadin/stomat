package com.stomat.domain.schedule;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.transfer.views.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Anton Chelyadin.
 * @since 29.12.18.
 */
@Entity
public class ExtraSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.ScheduleView.class)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotNull
    @JsonView(Views.ScheduleView.class)
    private LocalDateTime fromDate;

    @NotNull
    @JsonView(Views.ScheduleView.class)
    private LocalDateTime toDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonView(Views.ScheduleView.class)
    private ExtraScheduleTypeEnum type;

    @JsonView(Views.ScheduleView.class)
    private boolean allDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
