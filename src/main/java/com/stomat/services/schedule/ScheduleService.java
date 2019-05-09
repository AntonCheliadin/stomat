package com.stomat.services.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.Schedule;
import com.stomat.repository.schedule.ScheduleRepository;
import com.stomat.transfer.schedule.ScheduleDto;
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

    public Schedule addSchedule(Doctor doctor, ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setDoctor(doctor);
        return saveByDto(schedule, scheduleDto);
    }

    public Schedule saveByDto(Schedule schedule, ScheduleDto scheduleDto) {
        schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        schedule.setTimeFrom(scheduleDto.getTimeFrom());
        schedule.setTimeTo(scheduleDto.getTimeTo());
        return scheduleRepository.save(schedule);
    }
}
