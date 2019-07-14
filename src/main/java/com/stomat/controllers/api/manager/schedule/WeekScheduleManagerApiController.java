package com.stomat.controllers.api.manager.schedule;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.exceptions.NotFoundException;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.repository.schedule.WeekScheduleRepository;
import com.stomat.services.schedule.WeekScheduleService;
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

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
@RestController
@RequestMapping("/api/manage/schedule/week")
public class WeekScheduleManagerApiController {

    @Autowired
    public WeekScheduleManagerApiController(WeekScheduleRepository weekScheduleRepository,
                                            WeekScheduleService weekScheduleService, PermissionService permissionService, DoctorRepository doctorRepository) {
        this.weekScheduleRepository = weekScheduleRepository;
        this.weekScheduleService = weekScheduleService;
        this.permissionService = permissionService;
        this.doctorRepository = doctorRepository;
    }

    private final WeekScheduleRepository weekScheduleRepository;
    private final WeekScheduleService weekScheduleService;
    private final PermissionService permissionService;
    private final DoctorRepository doctorRepository;

    @GetMapping
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity getWeekSchedule(@AuthenticationPrincipal UserAccount currentUser,
                                          @RequestParam Doctor doctor) {
        if (doctor == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(doctor.getWeekSchedules());
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
        var doctor = doctorRepository.findById(scheduleDto.getDoctor()).orElseThrow(NotFoundException::new);

        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(weekScheduleService.addSchedule(doctor, scheduleDto));
    }

    @PutMapping("{id}")
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity updateWeekSchedule(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
            @Valid @RequestBody ScheduleDto scheduleDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        var doctor = doctorRepository.findById(scheduleDto.getDoctor()).orElseThrow(NotFoundException::new);

        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var scheduleWeek = weekScheduleRepository.findById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(weekScheduleService.updateSchedule(scheduleWeek, scheduleDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
            @Valid @RequestBody ScheduleDto scheduleDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        var doctor = doctorRepository.findById(scheduleDto.getDoctor()).orElseThrow(NotFoundException::new);

        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var scheduleWeek = weekScheduleRepository.findById(id).orElseThrow(NotFoundException::new);
        weekScheduleRepository.delete(scheduleWeek);
        return new ResponseEntity(HttpStatus.OK);
    }
}
