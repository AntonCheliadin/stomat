package com.stomat.controllers.admin;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.exceptions.NotFoundException;
import com.stomat.repository.user.UserRepository;
import com.stomat.services.profile.DoctorService;
import com.stomat.transfer.profile.DoctorDto;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adminOld/doctor")
public class DoctorAdminController {

    private DoctorService doctorService;
    private UserRepository userRepository;

    public DoctorAdminController(DoctorService doctorService, UserRepository userRepository) {
        this.doctorService = doctorService;
        this.userRepository = userRepository;
    }


    @Deprecated
    @GetMapping("/create")
    public String getCreateDoctor(@AuthenticationPrincipal UserAccount user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("doctor", new DoctorDto());

        return "admin/doctor/create";
    }

    @Deprecated
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

    @Deprecated
    @GetMapping("/update/{id}")
    public String getUpdateDoctor(@AuthenticationPrincipal UserAccount currentUser, @PathVariable("id") Doctor doctor, Model model) {
        if (doctor == null) {
            throw new NotFoundException("Doctor not found");
        }
        model.addAttribute("doctor", doctor);
        model.addAttribute("user", currentUser);
        return "admin/doctor/update";
    }

    @Deprecated
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

    @Deprecated
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

    @Deprecated
    @GetMapping("/list")
    public String getDoctorList(@AuthenticationPrincipal UserAccount currentUser, Model model) {
        var userAccount = userRepository.findById(currentUser.getId()).orElseThrow(NotFoundException::new);
        Collection<Doctor> doctors = userAccount.getDoctors().stream()
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .collect(Collectors.toList());

        model.addAttribute("doctors", doctors);
        model.addAttribute("user", currentUser);

        return "admin/doctor/list";
    }

    @Deprecated
    @GetMapping("/schedule")
    String getDoctorsSchedule(@AuthenticationPrincipal UserAccount currentUser, Model model) {
        model.addAttribute("user", currentUser);
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return "admin/doctor/schedule";
    }

    @Deprecated
    @GetMapping({"/schedule/{id}"})
    String getDoctorSchedule(@AuthenticationPrincipal UserAccount currentUser,
                             @PathVariable("id") long id, Model model) {
        model.addAttribute("user", currentUser);
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return "admin/doctor/schedule";
    }
}
