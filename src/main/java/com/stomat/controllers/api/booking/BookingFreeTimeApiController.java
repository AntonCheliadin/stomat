package com.stomat.controllers.api.booking;

import com.stomat.domain.booking.Reason;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.booking.ReasonRepository;
import com.stomat.services.booking.FreeTimeCalculationService;
import com.stomat.services.security.PermissionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class BookingFreeTimeApiController {

    private FreeTimeCalculationService freeTimeCalculationService;
    private PermissionService permissionService;
    private ReasonRepository reasonRepository;

    public BookingFreeTimeApiController(FreeTimeCalculationService freeTimeCalculationService, PermissionService permissionService, ReasonRepository reasonRepository) {
        this.freeTimeCalculationService = freeTimeCalculationService;
        this.permissionService = permissionService;
        this.reasonRepository = reasonRepository;
    }

    @RequestMapping(value = "/booking/free/time", method = RequestMethod.GET)
    public ResponseEntity freeTimes(@AuthenticationPrincipal UserAccount currentUser,
                                    @RequestParam Doctor doctor,
                                    @RequestParam Reason reason,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (doctor == null || from == null || to == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var freeTimes = freeTimeCalculationService.collectFreeTimes(doctor, reason, from, to);

        return ResponseEntity.ok(freeTimes);
    }
}
