package com.stomat.controllers.api.booking;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.user.UserAccount;
import com.stomat.services.booking.BookingService;
import com.stomat.transfer.booking.BookingDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(value = "/api/booking/submit", method = RequestMethod.POST)
    public ResponseEntity submitBooking(
            @AuthenticationPrincipal UserAccount currentUser,
            @RequestBody BookingDto bookingDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (!bookingService.validate(bookingDto)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        Booking booking = bookingService.submit(bookingDto, currentUser);

        return ResponseEntity.ok(booking);
    }
}