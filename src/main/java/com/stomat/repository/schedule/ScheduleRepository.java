package com.stomat.repository.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    Schedule findByDoctorAndDayOfWeek(Doctor doctor, int dayOfWeek);
}
