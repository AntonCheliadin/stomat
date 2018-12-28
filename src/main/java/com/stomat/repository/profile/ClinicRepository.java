package com.stomat.repository.profile;

import com.stomat.domain.profile.Clinic;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
public interface ClinicRepository extends CrudRepository<Clinic, Long> {

}
