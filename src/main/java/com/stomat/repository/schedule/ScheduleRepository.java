package com.stomat.repository.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.WeekSchedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<WeekSchedule, Long> {

    WeekSchedule findAllByDoctor(Doctor doctor);
    WeekSchedule findAllByDoctorAndDayOfWeek(Doctor doctor, int dayOfWeek);
}
