package com.stomat.domain.schedule;


import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.transfer.views.Views;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author Anton Chelyadin.
 * @since 29.12.18.
 */
@Entity
public class WeekSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.ScheduleView.class)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    /**
     * SUNDAY = 1
     * ...
     */
    @Min(1)
    @Max(7)
    @JsonView(Views.ScheduleView.class)
    private int dayOfWeek;//todo: try byte instead of int

    @NotNull
    @JsonView(Views.ScheduleView.class)
    private LocalTime timeFrom;

    @NotNull
    @JsonView(Views.ScheduleView.class)
    private LocalTime timeTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
