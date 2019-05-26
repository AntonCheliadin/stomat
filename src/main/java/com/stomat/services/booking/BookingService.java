package com.stomat.services.booking;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.booking.Patient;
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

    public BookingService(FreeTimeCalculationService freeTimeCalculationService, DoctorRepository doctorRepository,
                          BookingRepository bookingRepository) {
        this.freeTimeCalculationService = freeTimeCalculationService;
        this.doctorRepository = doctorRepository;
        this.bookingRepository = bookingRepository;
    }

    public boolean validate(BookingDto bookingDto) {
        Doctor doctor = doctorRepository.findById(bookingDto.getDoctor()).orElseThrow();

        List<FreeTimeDto> freeTimeDtos = freeTimeCalculationService.collectFreeTimes(doctor,
                bookingDto.getStartDate().toLocalDate(),
                bookingDto.getEndDate().toLocalDate().plusDays(1));

        return freeTimeDtos.stream().anyMatch(it -> bookingDto.getStartDate().equals(it.getFrom()));
    }

    public Booking create(BookingDto bookingDto) {
        Doctor doctor = doctorRepository.findById(bookingDto.getDoctor()).orElseThrow();

        //todo: find patient by phone at first
        Patient patient = new Patient(bookingDto.getFirstName(), bookingDto.getLastName(), bookingDto.getPhoneNumber());

        Booking booking = new Booking(patient, doctor, bookingDto.getStartDate(), bookingDto.getEndDate(), bookingDto.getDescription());

        return bookingRepository.save(booking);
    }

    public Booking move(Booking booking, LocalDateTime start, LocalDateTime end) {
        booking.setStartDate(start);
        booking.setEndDate(end);
//todo: don't create overlapping booking
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
