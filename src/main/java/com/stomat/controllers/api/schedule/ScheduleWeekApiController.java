package com.stomat.controllers.api.schedule;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.Schedule;
import com.stomat.domain.user.UserAccount;
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
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
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
                                     ScheduleService scheduleService, PermissionService permissionService) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
        this.permissionService = permissionService;
    }

    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;
    private final PermissionService permissionService;

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
            @AuthenticationPrincipal UserAccount currentUser, @RequestParam Doctor doctor,
            @Valid @RequestBody ScheduleDto scheduleDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!permissionService.isAccessAllowed(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(scheduleService.addSchedule(doctor, scheduleDto));
    }

    @PutMapping("{id}")
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity updateWeekSchedule(
            @AuthenticationPrincipal UserAccount currentUser, @RequestParam Doctor doctor, @PathVariable("id") long id,
            @Valid @RequestBody ScheduleDto scheduleDto, BindingResult bindingResult, Model model) {

        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);

        if (scheduleOptional.isEmpty() || bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!permissionService.isAccessAllowed(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(scheduleService.saveByDto(scheduleOptional.get(), scheduleDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(
            @AuthenticationPrincipal UserAccount currentUser, @RequestParam Doctor doctor, @PathVariable("id") long id,
            Model model) {
        if (!permissionService.isAccessAllowed(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        scheduleRepository.delete(schedule);
        return new ResponseEntity(HttpStatus.OK);
    }
}
