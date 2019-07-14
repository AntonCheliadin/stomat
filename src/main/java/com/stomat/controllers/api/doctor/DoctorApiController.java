package com.stomat.controllers.api.doctor;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.profile.Doctor;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.transfer.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor")
public class DoctorApiController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/list")
    @JsonView(Views.DoctorView.class)
    public ResponseEntity doctorList() {
        Iterable<Doctor> doctors = doctorRepository.findAll();

        return ResponseEntity.ok(doctors);
    }
}
