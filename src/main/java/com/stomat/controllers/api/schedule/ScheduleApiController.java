package com.stomat.controllers.api.schedule;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.schedule.Schedule;
import com.stomat.domain.schedule.ScheduleAdditionalTime;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.schedule.ScheduleAdditionalTimeRepository;
import com.stomat.transfer.schedule.PeriodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
@RestController
public class ScheduleApiController {

    @Autowired
    public ScheduleApiController(ScheduleAdditionalTimeRepository additionalTimeRepository) {
        this.additionalTimeRepository = additionalTimeRepository;
    }

    private final ScheduleAdditionalTimeRepository additionalTimeRepository;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = {"/schedule/bookings"}, method = RequestMethod.GET)
    public List<Booking> getBookings(@AuthenticationPrincipal UserAccount currentUser,
                                     @Valid PeriodDto periodDto, BindingResult bindingResult, Model model) {

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
    @RequestMapping(value = {"/schedule/week"}, method = RequestMethod.GET)
    public Collection<Schedule> getWeekSchedule(
            @AuthenticationPrincipal UserAccount currentUser) {

        return currentUser.getDoctor().getSchedules();
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = {"/schedule/additionalTimes"}, method = RequestMethod.GET)
    public Collection<ScheduleAdditionalTime> getAdditionalTimes(
            @AuthenticationPrincipal UserAccount currentUser,
            @Valid PeriodDto periodDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "period is not defined"
            );
        }

        return additionalTimeRepository.findAllByDoctorEqualsAndFromDateAfterAndToDateBefore(currentUser.getDoctor(),
                periodDto.getFrom(), periodDto.getTo());
    }
}
