package com.stomat.services.booking;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.booking.Patient;
import com.stomat.domain.booking.Reason;
import com.stomat.domain.profile.Doctor;
import com.stomat.repository.booking.BookingRepository;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.transfer.booking.BookingDto;
import com.stomat.transfer.booking.FreeTimeDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Anton Chelyadin.
 * @since 02.01.19.
 */
@Service
public class BookingService {

    private FreeTimeCalculationService freeTimeCalculationService;
    private DoctorRepository doctorRepository;
    private BookingRepository bookingRepository;
    private ReasonService reasonService;

    public BookingService(FreeTimeCalculationService freeTimeCalculationService, DoctorRepository doctorRepository,
                          BookingRepository bookingRepository, ReasonService reasonService) {
        this.freeTimeCalculationService = freeTimeCalculationService;
        this.doctorRepository = doctorRepository;
        this.bookingRepository = bookingRepository;
        this.reasonService = reasonService;
    }

    public boolean validate(BookingDto bookingDto) {
        Doctor doctor = doctorRepository.findById(bookingDto.getDoctor()).orElseThrow();
        Reason reason = reasonService.findByIdOrGetDefaults(bookingDto.getReason());

        List<FreeTimeDto> freeTimeDtos = freeTimeCalculationService.collectFreeTimes(doctor, reason,
                bookingDto.getStartDate().toLocalDate(),
                bookingDto.getEndDate().toLocalDate().plusDays(1));

        return freeTimeDtos.stream().anyMatch(it -> bookingDto.getStartDate().equals(it.getFrom()));
    }

    public Booking create(BookingDto bookingDto, Doctor doctor, Reason reason) {
        //todo: find patient by phone at first
        var patient = new Patient(bookingDto.getFirstName(), bookingDto.getLastName(), bookingDto.getPhoneNumber());

        var booking = new Booking(patient, doctor, reason, bookingDto.getStartDate(), bookingDto.getEndDate(),
                bookingDto.getDescription());

        return bookingRepository.save(booking);
    }

    public Booking move(Booking booking, LocalDateTime start, LocalDateTime end) {
        booking.setStartDate(start);
        booking.setEndDate(end);

        return bookingRepository.save(booking);
    }

    public Booking update(Booking booking, BookingDto bookingDto) {
        var patient = booking.getPatient();

        patient.setFirstName(bookingDto.getFirstName());
        patient.setLastName(bookingDto.getLastName());
        patient.setPhone(bookingDto.getPhoneNumber());

        booking.setStartDate(bookingDto.getStartDate());
        booking.setEndDate(bookingDto.getEndDate());
        booking.setDescription(bookingDto.getDescription());

        return bookingRepository.save(booking);
    }

    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }
}
