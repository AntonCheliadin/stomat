package com.stomat.repository.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ExtraSchedule;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ScheduleAdditionalTimeRepository extends CrudRepository<ExtraSchedule, Long> {

    Collection<ExtraSchedule> findAllByDoctorEqualsAndFromDateAfterAndToDateBefore(Doctor doctor, LocalDateTime from, LocalDateTime to);
}
