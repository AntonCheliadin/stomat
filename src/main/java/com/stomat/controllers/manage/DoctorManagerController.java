package com.stomat.controllers.manage;

import com.stomat.domain.profile.Doctor;
import com.stomat.repository.profile.DoctorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/manage/doctor")
public class DoctorManagerController {

    private DoctorRepository doctorRepository;

    public DoctorManagerController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/booking/{id}")
    String getBooking(@PathVariable("id") long id, Model model) {

        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

        if (optionalDoctor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor Not Found");
        }

        model.addAttribute("doctor", optionalDoctor.get());
        return "manage/doctor/booking";
    }

}
