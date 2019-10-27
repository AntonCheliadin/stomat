package com.stomat.controllers.api.manager;

import com.stomat.services.sms.SmsApiService;
import com.stomat.payload.sms.SendSmsPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sms")
public class SmsApiController {

    private SmsApiService smsApiService;

    public SmsApiController(SmsApiService smsApiService) {
        this.smsApiService = smsApiService;
    }

    @PostMapping("/send")
    public ResponseEntity sendSms(@Valid @RequestBody SendSmsPayload payload) {
        String response = smsApiService.sendSms(payload.getPhone(), payload.getMessage());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity getBalance() {
        String response = smsApiService.getBalance();

        return ResponseEntity.ok(response);
    }
}
