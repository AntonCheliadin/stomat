package com.stomat.services.booking;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.booking.Patient;
import com.stomat.exceptions.NotFoundException;
import com.stomat.repository.booking.BookingRepository;
import com.stomat.repository.booking.PatientRepository;
import com.stomat.repository.booking.ReasonRepository;
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
    private BookingRepository bookingRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private ReasonRepository reasonRepository;

    public BookingService(FreeTimeCalculationService freeTimeCalculationService,
                          BookingRepository bookingRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, ReasonRepository reasonRepository) {
        this.freeTimeCalculationService = freeTimeCalculationService;
        this.bookingRepository = bookingRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.reasonRepository = reasonRepository;
    }

    public boolean hasFreeTime(BookingDto bookingDto) {
        var doctor = doctorRepository.findById(bookingDto.getDoctor()).orElseThrow(NotFoundException::new);
        var reason = reasonRepository.findById(bookingDto.getReason()).orElseThrow(NotFoundException::new);

        List<FreeTimeDto> freeTimeDtos = freeTimeCalculationService.collectFreeTimes(doctor, reason,
                bookingDto.getStartDate().toLocalDate(),
                bookingDto.getEndDate().toLocalDate().plusDays(1));

        return freeTimeDtos.stream().anyMatch(it -> bookingDto.getStartDate().equals(it.getFrom()));
    }

    public Booking create(BookingDto bookingDto) {
        var doctor = doctorRepository.findById(bookingDto.getDoctor()).orElseThrow(NotFoundException::new);
        var reason = reasonRepository.findById(bookingDto.getReason()).orElseThrow(NotFoundException::new);

        var patient = patientRepository.findByFirstNameAndLastNameAndPhone(
                bookingDto.getFirstName(), bookingDto.getLastName(), bookingDto.getPhoneNumber())
                .orElse(
                        new Patient(bookingDto.getFirstName(), bookingDto.getLastName(), bookingDto.getPhoneNumber())
                );

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
