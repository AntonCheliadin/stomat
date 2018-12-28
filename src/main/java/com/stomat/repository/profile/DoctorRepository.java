package com.stomat.repository.profile;

import com.stomat.domain.profile.Doctor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

}
