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
    private DoctorRepository doctorRepository;
    private ReasonRepository reasonRepository;

    public PatientBookingApiController(BookingService bookingService, DoctorRepository doctorRepository, ReasonRepository reasonRepository) {
        this.bookingService = bookingService;
        this.doctorRepository = doctorRepository;
        this.reasonRepository = reasonRepository;
    }

    @PostMapping(value = "/create")
    @JsonView(Views.BookingsView.class)
    public ResponseEntity submitBookingByPatient(
            @Valid @RequestBody BookingDto bookingDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        var optDoc = doctorRepository.findById(bookingDto.getDoctor());
        var optReason = reasonRepository.findById(bookingDto.getReason());
        if (optDoc.isEmpty() || optReason.isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (!bookingService.hasConflictsBooking(bookingDto, optDoc.get(), optReason.get())) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        Booking booking = bookingService.create(bookingDto, optDoc.get(), optReason.get());

        return ResponseEntity.ok(booking);
    }

}
