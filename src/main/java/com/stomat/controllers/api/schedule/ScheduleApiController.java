package com.stomat.controllers.api.schedule;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.schedule.AdditionalTimeTypeEnum;
import com.stomat.domain.schedule.ScheduleAdditionalTime;
import com.stomat.domain.user.UserAccount;
import com.stomat.transfer.schedule.PeriodDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
@RestController
public class ScheduleApiController {

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = {"/schedule/bookings"}, method = RequestMethod.GET)
    public List<Booking> getBookings(@AuthenticationPrincipal UserAccount currentUser,
                                     Authentication authentication,
                                     Principal principal,
                                     @Valid PeriodDto periodDto, BindingResult bindingResult, Model model) {

        System.out.println("------------------");
        System.out.println(currentUser);
        System.out.println(principal);
        System.out.println(authentication);
        System.out.println("------------------");
        if (bindingResult.hasErrors()) {
            System.out.println("has errors");
        } else {
            System.out.println("valid");
        }
        System.out.println(periodDto.getFrom());
        System.out.println(periodDto.getTo());

        var bookings = new ArrayList<Booking>() {{
            add(new Booking() {{
                setId(1L);
            }});
        }};
        return bookings;
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = {"/schedule/additionalTimes"}, method = RequestMethod.GET)
    public List<ScheduleAdditionalTime> getAdditionalTimes() {
        var times = new ArrayList<ScheduleAdditionalTime>() {{
            add(new ScheduleAdditionalTime() {{
                setType(AdditionalTimeTypeEnum.ADDING);
            }});
        }};
        return times;
    }
}