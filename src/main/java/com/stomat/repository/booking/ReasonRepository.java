package com.stomat.repository.booking;

import com.stomat.domain.booking.Reason;
import org.springframework.data.repository.CrudRepository;

public interface ReasonRepository extends CrudRepository<Reason, Long> {

    Reason findByDefaults(boolean defaults);
}

