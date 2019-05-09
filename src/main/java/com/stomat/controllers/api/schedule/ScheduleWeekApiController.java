package com.stomat.controllers.api.schedule;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.Schedule;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.repository.schedule.ScheduleRepository;
import com.stomat.services.schedule.ScheduleService;
import com.stomat.services.security.PermissionService;
import com.stomat.transfer.schedule.ScheduleDto;
import com.stomat.transfer.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
@RestController
@RequestMapping("/schedule/week")
public class ScheduleWeekApiController {

    @Autowired
    public ScheduleWeekApiController(ScheduleRepository scheduleRepository,
                                     ScheduleService scheduleService, PermissionService permissionService, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
        this.permissionService = permissionService;
        this.doctorRepository = doctorRepository;
    }

    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;
    private final PermissionService permissionService;
    private final DoctorRepository doctorRepository;

    @GetMapping
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity getWeekSchedule(@AuthenticationPrincipal UserAccount currentUser,
                                          @RequestParam Doctor doctor) {
        if (doctor == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!permissionService.isAccessAllowed(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(doctor.getSchedules());
    }

    @PostMapping
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity addWeekSchedule(
            @AuthenticationPrincipal UserAccount currentUser,
            @Valid @RequestBody ScheduleDto scheduleDto,
            BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Optional<Doctor> optionalDoctor = doctorRepository.findById(scheduleDto.getDoctor());
        if (optionalDoctor.isEmpty() || !permissionService.isAccessAllowed(currentUser, optionalDoctor.get())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(scheduleService.addSchedule(optionalDoctor.get(), scheduleDto));
    }

    @PutMapping("{id}")
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity updateWeekSchedule(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
            @Valid @RequestBody ScheduleDto scheduleDto, BindingResult bindingResult, Model model) {

        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(scheduleDto.getDoctor());

        if (scheduleOptional.isEmpty() || optionalDoctor.isEmpty() || bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!permissionService.isAccessAllowed(currentUser, optionalDoctor.get())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(scheduleService.saveByDto(scheduleOptional.get(), scheduleDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@AuthenticationPrincipal UserAccount currentUser, @RequestParam Doctor doctor,
                                     @PathVariable("id") long id, Model model) {
        if (!permissionService.isAccessAllowed(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        scheduleRepository.delete(schedule);
        return new ResponseEntity(HttpStatus.OK);
    }
}
