package com.stomat.domain.schedule;

import com.stomat.domain.profile.Doctor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Anton Chelyadin.
 * @since 29.12.18.
 */
@Entity
public class ScheduleAdditionalTime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdditionalTimeTypeEnum type;

    private boolean allDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
