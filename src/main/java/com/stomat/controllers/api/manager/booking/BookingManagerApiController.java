package com.stomat.controllers.api.manager.booking;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.booking.Booking;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.booking.BookingRepository;
import com.stomat.repository.booking.ReasonRepository;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.services.booking.BookingService;
import com.stomat.services.security.PermissionService;
import com.stomat.transfer.booking.BookingDto;
import com.stomat.transfer.booking.MoveBookingDto;
import com.stomat.transfer.views.Views;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/api/manage/bookings")
@RestController
@Validated
public class BookingManagerApiController {

    private BookingService bookingService;
    private PermissionService permissionService;
    private BookingRepository bookingRepository;
    private DoctorRepository doctorRepository;
    private ReasonRepository reasonRepository;

    public BookingManagerApiController(BookingService bookingService, PermissionService permissionService, BookingRepository bookingRepository, DoctorRepository doctorRepository, ReasonRepository reasonRepository) {
        this.bookingService = bookingService;
        this.permissionService = permissionService;
        this.bookingRepository = bookingRepository;
        this.doctorRepository = doctorRepository;
        this.reasonRepository = reasonRepository;
    }

    @GetMapping("/list")
    @JsonView(Views.BookingsView.class)
    public ResponseEntity list(@AuthenticationPrincipal UserAccount currentUser,
                               @RequestParam Doctor doctor,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        List<Booking> bookings = bookingRepository.findAllByDoctorEqualsAndStartDateBetween(doctor,
                from.atStartOfDay(), to.atStartOfDay());

        return ResponseEntity.ok(bookings);
    }

    @PostMapping()
    @JsonView(Views.BookingsView.class)
    public ResponseEntity createBooking(@AuthenticationPrincipal UserAccount currentUser,
                                        @Valid @RequestBody BookingDto bookingDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        var optDoc = doctorRepository.findById(bookingDto.getDoctor());
        var optReason = reasonRepository.findById(bookingDto.getReason());
        if (optDoc.isEmpty() || optReason.isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (permissionService.isAccessDenied(currentUser, optDoc.get())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Booking booking = bookingService.create(bookingDto, optDoc.get(), optReason.get());

        return ResponseEntity.ok(booking);
    }

    @PutMapping("/move/{id}")
    @JsonView(Views.BookingsView.class)
    public ResponseEntity moveBooking(@AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
                                      @RequestBody MoveBookingDto moveBookingDto, BindingResult bindingResult, Model model) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);

        if (bookingOpt.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Doctor doctor = bookingOpt.get().getDoctor();

        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        var booking = bookingService.move(bookingOpt.get(), moveBookingDto.getStartDate(), moveBookingDto.getEndDate());

        return ResponseEntity.ok(booking);
    }

    @PutMapping("{id}")
    @JsonView(Views.BookingsView.class)
    public ResponseEntity updateBooking(@AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id,
                                        @Valid @RequestBody BookingDto bookingDto, BindingResult bindingResult, Model model) {

        Optional<Booking> bookingOpt = bookingRepository.findById(id);

        if (bookingOpt.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Doctor doctor = bookingOpt.get().getDoctor();

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        bookingService.update(bookingOpt.get(), bookingDto);

        return ResponseEntity.ok(bookingOpt.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBooking(@AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") long id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);

        if (bookingOpt.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Doctor doctor = bookingOpt.get().getDoctor();
        if (permissionService.isAccessDenied(currentUser, doctor)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        bookingService.delete(bookingOpt.get());

        return new ResponseEntity(HttpStatus.OK);
    }
}
