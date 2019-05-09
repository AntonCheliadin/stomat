package com.stomat.controllers.api.schedule;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.transfer.schedule.PeriodDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
@RestController
@RequestMapping("/schedule/bookings")
public class ScheduleBookingsApiController {//todo: implement

    @GetMapping
    public List<Booking> getBookings(@AuthenticationPrincipal UserAccount currentUser,
                                     @RequestBody Doctor doctor,
                                     @Valid PeriodDto periodDto, BindingResult bindingResult, Model model) {

        var bookings = new ArrayList<Booking>() {{
            add(new Booking() {{
                setId(1L);
            }});
        }};
        return bookings;
    }
}
