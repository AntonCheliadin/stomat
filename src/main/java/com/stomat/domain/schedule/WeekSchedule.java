package com.stomat.domain.schedule;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.transfer.views.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * @author Anton Chelyadin.
 * @since 29.12.18.
 */
@Entity
public class WeekSchedule implements Serializable {

    public WeekSchedule() {
    }

    public WeekSchedule(Doctor doctor) {
        this.doctor = doctor;
    }

    public WeekSchedule(Doctor doctor, DayOfWeek dayOfWeek, @NotNull LocalTime timeFrom, @NotNull LocalTime timeTo) {
        this.doctor = doctor;
        this.dayOfWeek = dayOfWeek;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.ScheduleView.class)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private DayOfWeek dayOfWeek;

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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @JsonView(Views.ScheduleView.class)
    @JsonProperty("dayOfWeek")
    public int getDayOfWeekNumber() {
        return dayOfWeek.getValue();
    }
}
