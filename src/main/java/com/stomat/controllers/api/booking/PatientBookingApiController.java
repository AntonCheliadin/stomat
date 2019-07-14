package com.stomat.controllers.api.booking;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.booking.Booking;
import com.stomat.repository.booking.ReasonRepository;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.services.booking.BookingService;
import com.stomat.transfer.booking.BookingDto;
import com.stomat.transfer.views.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(value = "/api/booking/patient")
@RestController
public class PatientBookingApiController {

    private BookingService bookingService;

    public PatientBookingApiController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping()
    @JsonView(Views.BookingsView.class)
    public ResponseEntity submitBookingByPatient(
            @Valid @RequestBody BookingDto bookingDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (!bookingService.hasFreeTime(bookingDto)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        Booking booking = bookingService.create(bookingDto);

        return ResponseEntity.ok(booking);
    }

}
