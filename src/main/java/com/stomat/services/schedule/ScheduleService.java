package com.stomat.services.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.WeekSchedule;
import com.stomat.repository.schedule.ScheduleRepository;
import com.stomat.transfer.schedule.ScheduleDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author Anton Chelyadin.
 * @since 02.01.19.
 */
@Service
public class ScheduleService {

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    private final ScheduleRepository scheduleRepository;

    public WeekSchedule addSchedule(Doctor doctor, ScheduleDto scheduleDto) {
        return saveByDto(new WeekSchedule(doctor), scheduleDto);
    }

    public WeekSchedule saveByDto(WeekSchedule weekSchedule, ScheduleDto scheduleDto) {
        BeanUtils.copyProperties(scheduleDto, weekSchedule, "doctor", "id");
        return scheduleRepository.save(weekSchedule);
    }
}
