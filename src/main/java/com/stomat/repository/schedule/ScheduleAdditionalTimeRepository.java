package com.stomat.repository.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ScheduleAdditionalTime;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

public interface ScheduleAdditionalTimeRepository extends CrudRepository<ScheduleAdditionalTime, Long> {

    Collection<ScheduleAdditionalTime> findAllByDoctorEqualsAndFromDateAfterAndToDateBefore(Doctor doctor, LocalDateTime from, LocalDateTime to);
}
