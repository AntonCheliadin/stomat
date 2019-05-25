package com.stomat.services.booking;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.user.UserAccount;
import com.stomat.transfer.booking.BookingDto;
import org.springframework.stereotype.Service;

/**
 * @author Anton Chelyadin.
 * @since 02.01.19.
 */
@Service
public class BookingService {

    public boolean validate(BookingDto bookingDto) {
        return false;
    }

    public Booking submit(BookingDto bookingDto, UserAccount currentUser) {

        return null;
    }
}
