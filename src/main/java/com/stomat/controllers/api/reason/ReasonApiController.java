package com.stomat.controllers.api.reason;

import com.stomat.domain.booking.Reason;
import com.stomat.repository.booking.ReasonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reason")
public class ReasonApiController {

    private ReasonRepository reasonRepository;

    public ReasonApiController(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    @GetMapping("/list")
    public ResponseEntity getReasons() {
        Iterable<Reason> allReasons = reasonRepository.findAll();

        return ResponseEntity.ok(allReasons);
    }
}
