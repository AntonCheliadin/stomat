package com.stomat.services.schedule;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.WeekSchedule;
import com.stomat.repository.schedule.WeekScheduleRepository;
import com.stomat.transfer.schedule.ScheduleDto;
import org.springframework.stereotype.Service;

/**
 * @author Anton Chelyadin.
 * @since 02.01.19.
 */
@Service
public class WeekScheduleService {

    public WeekScheduleService(WeekScheduleRepository weekScheduleRepository) {
        this.weekScheduleRepository = weekScheduleRepository;
    }

    private final WeekScheduleRepository weekScheduleRepository;

    public WeekSchedule addSchedule(Doctor doctor, ScheduleDto scheduleDto) {
        var weekSchedule = new WeekSchedule(doctor, scheduleDto.getDayOfWeek(),
                scheduleDto.getTimeFrom(), scheduleDto.getTimeTo());
        return weekScheduleRepository.save(weekSchedule);
    }

    public WeekSchedule updateSchedule(WeekSchedule weekSchedule, ScheduleDto scheduleDto) {
        weekSchedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        weekSchedule.setTimeFrom(scheduleDto.getTimeFrom());
        weekSchedule.setTimeTo(scheduleDto.getTimeTo());
        return weekScheduleRepository.save(weekSchedule);
    }
}
