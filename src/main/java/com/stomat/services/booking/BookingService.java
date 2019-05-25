package com.stomat.services.booking;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.booking.Patient;
import com.stomat.domain.profile.Doctor;
import com.stomat.repository.booking.BookingRepository;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.transfer.booking.BookingDto;
import com.stomat.transfer.booking.FreeTimeDto;
import org.springframework.stereotype.Service;

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
                bookingDto.getStart().toLocalDate(),
                bookingDto.getEnd().toLocalDate().plusDays(1));

        return freeTimeDtos.stream().anyMatch(it -> bookingDto.getStart().equals(it.getFrom()));
    }

    public Booking create(BookingDto bookingDto) {
        Doctor doctor = doctorRepository.findById(bookingDto.getDoctor()).orElseThrow();

        Patient patient = new Patient(bookingDto.getFirstName(), bookingDto.getLastName(), bookingDto.getPhoneNumber());

        Booking booking = new Booking(patient, doctor, bookingDto.getStart(), bookingDto.getEnd(), bookingDto.getDescription());

        return bookingRepository.save(booking);
    }
}
