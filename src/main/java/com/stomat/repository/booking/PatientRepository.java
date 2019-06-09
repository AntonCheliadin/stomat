package com.stomat.repository.booking;

import com.stomat.domain.booking.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    Optional<Patient> findByFirstNameAndLastNameAndPhone(String firstName, String lastName, String phone);
}

