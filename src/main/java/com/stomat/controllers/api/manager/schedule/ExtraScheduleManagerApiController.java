package com.stomat.controllers.api.manager.schedule;


import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.exceptions.NotFoundException;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.repository.schedule.ExtraScheduleRepository;
import com.stomat.services.schedule.ExtraScheduleService;
import com.stomat.services.security.PermissionService;
import com.stomat.transfer.schedule.ExtraScheduleDto;
import com.stomat.transfer.views.Views;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/manage/schedule/extra")
public class ExtraScheduleManagerApiController {

    public ExtraScheduleManagerApiController(
            ExtraScheduleRepository additionalTimeRepository,
            PermissionService permissionService, DoctorRepository doctorRepository,
            ExtraScheduleService extraScheduleService) {
        this.additionalTimeRepository = additionalTimeRepository;
        this.permissionService = permissionService;
        this.doctorRepository = doctorRepository;
        this.extraScheduleService = extraScheduleService;
    }

    private final ExtraScheduleRepository additionalTimeRepository;
    private final PermissionService permissionService;
    private final DoctorRepository doctorRepository;
    private final ExtraScheduleService extraScheduleService;

    @GetMapping
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity getExtraSchedule(
            @AuthenticationPrincipal UserAccount currentUser, @RequestParam Doctor doctor,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (doctor == null) {
            throw new NotFoundException("Doctor not found");
        }

        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var extraTimes = additionalTimeRepository.findAllByDoctorEqualsAndFromDateAfterAndFromDateBefore(
                doctor, from.atStartOfDay(), to.atStartOfDay());

        return ResponseEntity.ok(extraTimes);
    }

    @PostMapping
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity createExtraSchedule(
            @AuthenticationPrincipal UserAccount currentUser,
            @RequestBody ExtraScheduleDto extraScheduleDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        var doctor = doctorRepository.findById(extraScheduleDto.getDoctor()).orElseThrow(NotFoundException::new);

        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var extraSchedule = extraScheduleService.addExtraSchedule(doctor, extraScheduleDto);

        return ResponseEntity.ok(extraSchedule);
    }

    @PutMapping("{id}")
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity updateExtraSchedule(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
            @RequestBody ExtraScheduleDto extraScheduleDto, BindingResult bindingResult, Model model) {

        var extraSchedule = additionalTimeRepository.findById(id).orElseThrow(NotFoundException::new);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (permissionService.isAccessDenied(currentUser, extraSchedule.getDoctor())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        extraScheduleService.saveByDto(extraSchedule, extraScheduleDto);

        return ResponseEntity.ok(extraSchedule);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteExtraSchedule(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id) {

        var extraSchedule = additionalTimeRepository.findById(id).orElseThrow(NotFoundException::new);

        if (permissionService.isAccessDenied(currentUser, extraSchedule.getDoctor())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        additionalTimeRepository.delete(extraSchedule);

        return new ResponseEntity(HttpStatus.OK);
    }

}
