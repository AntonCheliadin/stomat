package com.stomat.domain.profile;

import com.stomat.domain.schedule.Schedule;
import com.stomat.domain.schedule.ScheduleBlocker;
import com.stomat.domain.user.UserAccount;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Anton Chelyadin.
 * @since 28.12.18.
 */
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ScheduleBlocker> blockers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<ScheduleBlocker> getBlockers() {
        return blockers;
    }

    public void setBlockers(Set<ScheduleBlocker> blockers) {
        this.blockers = blockers;
    }
}
