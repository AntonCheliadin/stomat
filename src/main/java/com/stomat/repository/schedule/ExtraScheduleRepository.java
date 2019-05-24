package com.stomat.repository.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ExtraSchedule;
import com.stomat.domain.schedule.ExtraScheduleTypeEnum;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ExtraScheduleRepository extends CrudRepository<ExtraSchedule, Long> {

    Collection<ExtraSchedule> findAllByDoctorEqualsAndFromDateAfterAndFromDateBefore(
            Doctor doctor, LocalDateTime from, LocalDateTime to);

    Collection<ExtraSchedule> findAllByDoctorEqualsAndFromDateAfterAndFromDateBeforeAndType(
            Doctor doctor, LocalDateTime from, LocalDateTime to, ExtraScheduleTypeEnum type);
}
