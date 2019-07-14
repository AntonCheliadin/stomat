package com.stomat.services.profile;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.exceptions.NotFoundException;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.repository.user.UserRepository;
import com.stomat.transfer.profile.DoctorDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class DoctorService {

    private DoctorRepository doctorRepository;
    private UserRepository userRepository;

    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public Doctor create(DoctorDto doctorDto, Long userId) {
        UserAccount user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Doctor doctor = new Doctor(doctorDto.getFirstName(), doctorDto.getFathersName(), doctorDto.getLastName(), doctorDto.getEmail());
        doctor.setManagers(Set.of(user));

        return doctorRepository.save(doctor);
    }

    public Doctor update(DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(doctorDto.getId()).orElseThrow(NotFoundException::new);

        BeanUtils.copyProperties(doctorDto, doctor, "id");

        return doctorRepository.save(doctor);
    }
}
