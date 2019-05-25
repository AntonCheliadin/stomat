package com.stomat.services.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.WeekSchedule;
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

    public WeekSchedule addSchedule(Doctor doctor, ScheduleDto scheduleDto) {
        var weekSchedule = new WeekSchedule(doctor, scheduleDto.getDayOfWeek(),
                scheduleDto.getTimeFrom(), scheduleDto.getTimeTo());
        return scheduleRepository.save(weekSchedule);
    }

    public WeekSchedule updateSchedule(WeekSchedule weekSchedule, ScheduleDto scheduleDto) {
        weekSchedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        weekSchedule.setTimeFrom(scheduleDto.getTimeFrom());
        weekSchedule.setTimeTo(scheduleDto.getTimeTo());
        return scheduleRepository.save(weekSchedule);
    }
}
