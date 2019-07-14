package com.stomat.controllers.booking;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientBookingController {

    @GetMapping("/booking")
    public String patientBooking(Model model) {

        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return "booking/patient/index";
    }
}
