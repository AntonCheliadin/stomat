package com.stomat.controllers.api.schedule;


import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.ScheduleAdditionalTime;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.schedule.ScheduleAdditionalTimeRepository;
import com.stomat.services.security.PermissionService;
import com.stomat.transfer.schedule.PeriodDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/schedule/extra")
public class ScheduleExtraApiController {

    public ScheduleExtraApiController(ScheduleAdditionalTimeRepository additionalTimeRepository, PermissionService permissionService) {
        this.additionalTimeRepository = additionalTimeRepository;
        this.permissionService = permissionService;
    }

    private final ScheduleAdditionalTimeRepository additionalTimeRepository;
    private final PermissionService permissionService;

    @GetMapping
    public Collection<ScheduleAdditionalTime> getAdditionalTimes(
            @AuthenticationPrincipal UserAccount currentUser,
            @RequestParam Doctor doctor,
            @Valid PeriodDto periodDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request parameters are incorrect."
            );
        }

        if (!permissionService.isAccessAllowed(currentUser, doctor)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Current user hasn't access to manage doctor."
            );
        }

        return additionalTimeRepository.findAllByDoctorEqualsAndFromDateAfterAndToDateBefore(doctor,
                periodDto.getFrom(), periodDto.getTo());
    }

}
