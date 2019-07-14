package com.stomat.controllers.manage;

import com.stomat.domain.profile.Doctor;
import com.stomat.repository.profile.DoctorRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/manage/doctors")
public class DoctorManagerController {

    @GetMapping()
    String getDoctorsManagement(Model model) {
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return "manage/doctor/booking";
    }

    @GetMapping({"/{id}"})
    String getDoctorManagement(@PathVariable("id") long id, Model model) {
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return "manage/doctor/booking";
    }

}
