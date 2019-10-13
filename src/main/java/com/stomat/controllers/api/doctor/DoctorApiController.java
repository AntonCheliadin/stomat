package com.stomat.controllers.api.doctor;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.exceptions.NotFoundException;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.services.profile.DoctorService;
import com.stomat.transfer.profile.DoctorDto;
import com.stomat.transfer.views.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/doctor")
public class DoctorApiController {

    private DoctorRepository doctorRepository;
    private DoctorService doctorService;

    public DoctorApiController(DoctorRepository doctorRepository, DoctorService doctorService) {
        this.doctorRepository = doctorRepository;
        this.doctorService = doctorService;
    }

    @GetMapping("/list")
    @JsonView(Views.DoctorView.class)
    public ResponseEntity doctorList() {
        Iterable<Doctor> doctors = doctorRepository.findAll();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    @JsonView(Views.DoctorView.class)
    public ResponseEntity<Doctor> doctorById(@PathVariable("id") long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(NotFoundException::new);

        return ResponseEntity.ok(doctor);
    }

    @PostMapping()
    @JsonView(Views.DoctorView.class)
    public ResponseEntity<Doctor> createDoctor(@AuthenticationPrincipal UserAccount userAccount,
                                               @Valid @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorService.create(doctorDto, userAccount.getId());

        return ResponseEntity.ok(doctor);
    }

    @PutMapping("/{id}")
    @JsonView(Views.DoctorView.class)
    public ResponseEntity<Doctor> updateDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorService.update(doctorDto);

        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDoctor(@PathVariable("id") Doctor doctor) {
        if (doctor == null) {
            throw new NotFoundException("Doctor not found");
        }

        if (doctorService.canDoctorBeDeleted(doctor)) {
            doctorService.delete(doctor);
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.ok(HttpStatus.FORBIDDEN);
        }
    }
}
