package com.stomat.repository.booking;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.profile.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findAllByDoctorEqualsAndStartDateBetween(Doctor doctor, LocalDateTime from, LocalDateTime to);

    int countByDoctorEquals(Doctor doctor);
}

