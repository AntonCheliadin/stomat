package com.stomat.controllers.api.booking;

import com.stomat.domain.booking.Reason;
import com.stomat.domain.profile.Doctor;
import com.stomat.services.booking.FreeTimeCalculationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class BookingFreeTimeApiController {

    private FreeTimeCalculationService freeTimeCalculationService;

    public BookingFreeTimeApiController(FreeTimeCalculationService freeTimeCalculationService) {
        this.freeTimeCalculationService = freeTimeCalculationService;
    }

    @RequestMapping(value = "/api/booking/free/time", method = RequestMethod.GET)
    public ResponseEntity freeTimes(@RequestParam Doctor doctor,
                                    @RequestParam Reason reason,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        var freeTimes = freeTimeCalculationService.collectFreeTimes(doctor, reason, from, to);

        return ResponseEntity.ok(freeTimes);
    }
}
