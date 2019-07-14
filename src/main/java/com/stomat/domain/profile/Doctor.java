package com.stomat.domain.profile;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.schedule.ExtraSchedule;
import com.stomat.domain.schedule.WeekSchedule;
import com.stomat.domain.user.UserAccount;
import com.stomat.transfer.views.Views;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author Anton Chelyadin.
 * @since 28.12.18.
 */
@Entity
public class Doctor {

    public Doctor() {
    }

    public Doctor(String firstName, String fathersName, String lastName, String email) {
        this.firstName = firstName;
        this.fathersName = fathersName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({Views.ScheduleView.class, Views.DoctorView.class})
    private Long id;

    @NotBlank
    @JsonView(Views.DoctorView.class)
    private String firstName;

    @NotBlank
    @JsonView(Views.DoctorView.class)
    private String fathersName;

    @NotBlank
    @JsonView(Views.DoctorView.class)
    private String lastName;

    @NotBlank
    @JsonView(Views.DoctorView.class)
    private String email;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "doctor_manager",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<UserAccount> managers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WeekSchedule> weekSchedules;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExtraSchedule> extraSchedules;

    /* booking allowed after now plus minBookingTime (in minutes) */
    private Integer minBookingTime;

    @JsonView(Views.DoctorView.class)
    public String getFullName() {
        return String.join(" ", lastName, firstName, fathersName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserAccount> getManagers() {
        return managers;
    }

    public void setManagers(Set<UserAccount> managers) {
        this.managers = managers;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Set<WeekSchedule> getWeekSchedules() {
        return weekSchedules;
    }

    public void setWeekSchedules(Set<WeekSchedule> weekSchedules) {
        this.weekSchedules = weekSchedules;
    }

    public Set<ExtraSchedule> getExtraSchedules() {
        return extraSchedules;
    }

    public void setExtraSchedules(Set<ExtraSchedule> extraSchedules) {
        this.extraSchedules = extraSchedules;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public Integer getMinBookingTime() {
        return minBookingTime;
    }

    public void setMinBookingTime(Integer minBookingTime) {
        this.minBookingTime = minBookingTime;
    }
}
