package com.stomat.services.booking;

import com.stomat.domain.booking.Reason;
import com.stomat.repository.booking.ReasonRepository;
import org.springframework.stereotype.Service;

@Service
public class ReasonService {

    private ReasonRepository reasonRepository;

    public ReasonService(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    Reason findByIdOrGetDefaults(Long id) {
        if (id != null) {
            return reasonRepository.findById(id).orElseThrow();
        } else {
            return reasonRepository.findByDefaults(true);
        }
    }
}
