package com.stomat.repository.booking;

import com.stomat.domain.booking.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {

}

