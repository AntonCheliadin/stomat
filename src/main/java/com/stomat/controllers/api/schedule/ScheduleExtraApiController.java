package com.stomat.controllers.api.schedule;


import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ScheduleAdditionalTime;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.repository.schedule.ScheduleAdditionalTimeRepository;
import com.stomat.services.schedule.ExtraScheduleService;
import com.stomat.services.security.PermissionService;
import com.stomat.transfer.schedule.ExtraScheduleDto;
import com.stomat.transfer.schedule.PeriodDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity getAdditionalTimes(
            @AuthenticationPrincipal UserAccount currentUser,
            @Valid @RequestParam PeriodDto periodDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Optional<Doctor> optionalDoctor = doctorRepository.findById(periodDto.getDoctor());

        if (optionalDoctor.isEmpty() || !permissionService.isAccessAllowed(currentUser, optionalDoctor.get())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var extraTimes = additionalTimeRepository.findAllByDoctorEqualsAndFromDateAfterAndToDateBefore(
                optionalDoctor.get(), periodDto.getFrom().atStartOfDay(), periodDto.getTo().atStartOfDay());

        return ResponseEntity.ok(extraTimes);
    }

    @PostMapping
    public ResponseEntity createAdditionalTimes(
            @AuthenticationPrincipal UserAccount currentUser,
            @Valid @RequestParam ExtraScheduleDto extraScheduleDto, BindingResult bindingResult, Model model) {

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

    @PutMapping("${id}")
    public ResponseEntity updateAdditionalTimes(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
            @Valid @RequestParam ExtraScheduleDto extraScheduleDto, BindingResult bindingResult, Model model) {

        Optional<ScheduleAdditionalTime> optExtraSchedule = additionalTimeRepository.findById(id);
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

    @DeleteMapping("${id}")
    public ResponseEntity deleteAdditionalTimes(
            @AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
            @Valid @RequestParam ExtraScheduleDto extraScheduleDto, BindingResult bindingResult, Model model) {

        Optional<ScheduleAdditionalTime> optExtraSchedule = additionalTimeRepository.findById(id);
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
