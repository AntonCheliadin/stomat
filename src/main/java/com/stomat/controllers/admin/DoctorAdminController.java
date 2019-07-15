package com.stomat.controllers.admin;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.exceptions.NotFoundException;
import com.stomat.repository.user.UserRepository;
import com.stomat.services.profile.DoctorService;
import com.stomat.transfer.profile.DoctorDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/doctor")
public class DoctorAdminController {

    private DoctorService doctorService;
    private UserRepository userRepository;

    public DoctorAdminController(DoctorService doctorService, UserRepository userRepository) {
        this.doctorService = doctorService;
        this.userRepository = userRepository;
    }


    @GetMapping("/create")
    public String getCreateDoctor(Model model) {
        model.addAttribute("doctor", new DoctorDto());

        return "admin/doctor/create";
    }

    @PostMapping("/create")
    public String postCreateDoctor(@AuthenticationPrincipal UserAccount userAccount,
                                   @Valid DoctorDto doctorDto, BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctor", doctorDto);
            return "admin/doctor/create";
        }

        Doctor doctor = doctorService.create(doctorDto, userAccount.getId());

        return "redirect:/admin/doctor/update/" + doctor.getId();
    }

    @GetMapping("/update/{id}")
    public String getUpdateDoctor(@PathVariable("id") Doctor doctor, Model model) {
        if (doctor == null) {
            throw new NotFoundException("Doctor not found");
        }
        model.addAttribute("doctor", doctor);
        return "admin/doctor/update";
    }

    @PostMapping("/update/{id}")
    public String postUpdateDoctor(@Valid DoctorDto doctorDto, BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctor", doctorDto);
            return "admin/doctor/update";
        }

        Doctor doctor = doctorService.update(doctorDto);

        model.addAttribute("doctor", doctor);
        return "admin/doctor/update";
    }

    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Doctor doctor, Model model) {
        if (doctor == null) {
            throw new NotFoundException("Doctor not found");
        }

        if (doctorService.canDoctorBeDeleted(doctor)) {
            doctorService.delete(doctor);
            return "redirect:/admin/doctor/list";
        } else {
            model.addAttribute("doctor", doctor);
            return "admin/doctor/update";
        }
    }


    @GetMapping("/list")
    public String getDoctorList(@AuthenticationPrincipal UserAccount currentUser, Model model) {
        var userAccount = userRepository.findById(currentUser.getId()).orElseThrow(NotFoundException::new);
        Collection<Doctor> doctors = userAccount.getDoctors().stream()
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .collect(Collectors.toList());

        model.addAttribute("doctors", doctors);

        return "admin/doctor/list";
    }

}
