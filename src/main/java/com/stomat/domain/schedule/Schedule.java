package com.stomat.domain.schedule;


import com.stomat.domain.profile.Doctor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Anton Chelyadin.
 * @since 29.12.18.
 */
@Entity
public class Schedule implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @Id
    private Doctor doctor;

    /**
     * SUNDAY = 1
     * ...
     */
    @Id
    @Min(1)
    @Max(7)
    private int dayOfWeek;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date fimeFrom;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date timeTo;

    public Date getFimeFrom() {
        return fimeFrom;
    }

    public void setFimeFrom(Date fimeFrom) {
        this.fimeFrom = fimeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
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
