package com.stomat.controllers.api.doctor;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.exceptions.NotFoundException;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.transfer.views.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor")
public class DoctorApiController {

    private DoctorRepository doctorRepository;

    public DoctorApiController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
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
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        return ResponseEntity.ok(doctor);
    }
}
