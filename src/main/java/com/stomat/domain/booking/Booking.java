package com.stomat.domain.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.transfer.views.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;

/**
 * @author Anton Chelyadin.
 * @since 29.12.18.
 */
@Entity
public class Booking {

    public Booking() {
    }

    public Booking(Patient patient, Doctor doctor, Reason reason, LocalDateTime startDate, LocalDateTime endDate, String description) {
        this.patient = patient;
        this.doctor = doctor;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.BookingsView.class)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {PERSIST, MERGE, REFRESH})
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonView(Views.BookingsView.class)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotNull
    @JsonView(Views.BookingsView.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull
    @JsonView(Views.BookingsView.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @JsonView(Views.BookingsView.class)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = PERSIST)
    @JoinColumn(name = "reason_id", nullable = false)
    @JsonView(Views.BookingsView.class)
    private Reason reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }
}
