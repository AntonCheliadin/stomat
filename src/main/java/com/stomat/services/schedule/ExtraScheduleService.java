package com.stomat.services.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ScheduleAdditionalTime;
import com.stomat.repository.schedule.ScheduleAdditionalTimeRepository;
import com.stomat.transfer.schedule.ExtraScheduleDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ExtraScheduleService {

    private final ScheduleAdditionalTimeRepository scheduleAdditionalTimeRepository;

    public ExtraScheduleService(ScheduleAdditionalTimeRepository scheduleAdditionalTimeRepository) {
        this.scheduleAdditionalTimeRepository = scheduleAdditionalTimeRepository;
    }

    public ScheduleAdditionalTime addExtraSchedule(Doctor doctor, ExtraScheduleDto extraScheduleDto) {
        var schedule = new ScheduleAdditionalTime();
        schedule.setDoctor(doctor);
        return saveByDto(schedule, extraScheduleDto);
    }

    public ScheduleAdditionalTime saveByDto(ScheduleAdditionalTime schedule, ExtraScheduleDto scheduleDto) {
        BeanUtils.copyProperties(scheduleDto, schedule, "doctor", "id");
        return scheduleAdditionalTimeRepository.save(schedule);
    }
}
