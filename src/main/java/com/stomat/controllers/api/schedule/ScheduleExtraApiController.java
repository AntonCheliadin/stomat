package com.stomat.controllers.api.schedule;


import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ExtraSchedule;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.repository.schedule.ScheduleAdditionalTimeRepository;
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
import java.util.Optional;

@RestController
@RequestMapping("/schedule/extra")
public class ScheduleExtraApiController {

    public ScheduleExtraApiController(
            ScheduleAdditionalTimeRepository additionalTimeRepository,
            PermissionService permissionService, DoctorRepository doctorRepository,
            ExtraScheduleService extraScheduleService) {
        this.additionalTimeRepository = additionalTimeRepository;
        this.permissionService = permissionService;
        this.doctorRepository = doctorRepository;
        this.extraScheduleService = extraScheduleService;
    }

    private final ScheduleAdditionalTimeRepository additionalTimeRepository;
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
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (!permissionService.isAccessAllowed(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var extraTimes = additionalTimeRepository.findAllByDoctorEqualsAndFromDateAfterAndToDateBefore(
                doctor, from.atStartOfDay(), to.atStartOfDay());

        return ResponseEntity.ok(extraTimes);
    }
//todo: handle events on the whole day
    @PostMapping
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity createExtraSchedule(
            @AuthenticationPrincipal UserAccount currentUser,
            @RequestBody ExtraScheduleDto extraScheduleDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Optional<Doctor> optionalDoctor = doctorRepository.findById(extraScheduleDto.getDoctor());

        if (optionalDoctor.isEmpty() || !permissionService.isAccessAllowed(currentUser, optionalDoctor.get())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var extraSchedule = extraScheduleService.addExtraSchedule(optionalDoctor.get(), extraScheduleDto);

        return ResponseEntity.ok(extraSchedule);
    }

    @PutMapping("{id}")
    @JsonView(Views.ScheduleView.class)
    public ResponseEntity updateExtraSchedule(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
            @RequestBody ExtraScheduleDto extraScheduleDto, BindingResult bindingResult, Model model) {

        Optional<ExtraSchedule> optExtraSchedule = additionalTimeRepository.findById(id);
        if (optExtraSchedule.isEmpty() || bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Optional<Doctor> optionalDoctor = doctorRepository.findById(extraScheduleDto.getDoctor());

        if (optionalDoctor.isEmpty() || !permissionService.isAccessAllowed(currentUser, optionalDoctor.get())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var extraSchedule = extraScheduleService.saveByDto(optExtraSchedule.get(), extraScheduleDto);

        return ResponseEntity.ok(extraSchedule);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteExtraSchedule(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
            @RequestBody ExtraScheduleDto extraScheduleDto, BindingResult bindingResult, Model model) {

        Optional<ExtraSchedule> optExtraSchedule = additionalTimeRepository.findById(id);
        if (optExtraSchedule.isEmpty() || bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Optional<Doctor> optionalDoctor = doctorRepository.findById(extraScheduleDto.getDoctor());

        if (optionalDoctor.isEmpty() || !permissionService.isAccessAllowed(currentUser, optionalDoctor.get())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        additionalTimeRepository.delete(optExtraSchedule.get());

        return new ResponseEntity(HttpStatus.OK);
    }

}
