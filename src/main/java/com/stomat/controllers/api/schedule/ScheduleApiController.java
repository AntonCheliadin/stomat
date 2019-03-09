package com.stomat.controllers.api.schedule;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.Schedule;
import com.stomat.domain.schedule.ScheduleAdditionalTime;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.schedule.ScheduleAdditionalTimeRepository;
import com.stomat.repository.schedule.ScheduleRepository;
import com.stomat.services.schedule.ScheduleService;
import com.stomat.transfer.schedule.PeriodDto;
import com.stomat.transfer.schedule.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
@RestController
public class ScheduleApiController {

    @Autowired
    public ScheduleApiController(ScheduleAdditionalTimeRepository additionalTimeRepository,
                                 ScheduleRepository scheduleRepository,
                                 ScheduleService scheduleService) {
        this.additionalTimeRepository = additionalTimeRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
    }

    private final ScheduleAdditionalTimeRepository additionalTimeRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = {"/schedule/bookings"}, method = RequestMethod.GET)
    public List<Booking> getBookings(@AuthenticationPrincipal UserAccount currentUser,
                                     @Valid PeriodDto periodDto, BindingResult bindingResult, Model model) {

        var bookings = new ArrayList<Booking>() {{
            add(new Booking() {{
                setId(1L);
            }});
        }};
        return bookings;
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = {"/schedule/week"}, method = RequestMethod.GET)
    public Collection<Schedule> getWeekSchedule(
            @AuthenticationPrincipal UserAccount currentUser) {

        return currentUser.getDoctor().getSchedules();
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = {"/schedule/additionalTimes"}, method = RequestMethod.GET)
    public Collection<ScheduleAdditionalTime> getAdditionalTimes(
            @AuthenticationPrincipal UserAccount currentUser,
            @Valid PeriodDto periodDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request parameters are incorrect."
            );
        }

        return additionalTimeRepository.findAllByDoctorEqualsAndFromDateAfterAndToDateBefore(currentUser.getDoctor(),
                periodDto.getFrom(), periodDto.getTo());
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = {"/schedule/week/add"}, method = RequestMethod.POST)
    public ResponseEntity<Object> addWeekSchedule(
            @AuthenticationPrincipal UserAccount currentUser,
            @Valid @RequestBody ScheduleDto scheduleDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Doctor doctor = currentUser.getDoctor();

        Schedule existedSchedule = scheduleRepository.findByDoctorAndDayOfWeek(doctor, scheduleDto.getDayOfWeek());
        if (existedSchedule != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(scheduleService.addSchedule(doctor, scheduleDto));
    }

    @GetMapping("/schedule/week/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id, Model model) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        scheduleRepository.delete(schedule);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
