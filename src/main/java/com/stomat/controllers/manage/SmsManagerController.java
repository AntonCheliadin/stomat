package com.stomat.controllers.manage;

import com.stomat.services.sms.SmsApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manage/sms")
public class SmsManagerController {

    private SmsApiService smsApiService;

    public SmsManagerController(SmsApiService smsApiService) {
        this.smsApiService = smsApiService;
    }

    @GetMapping
    public String getSmsPage() {
        return "manage/sms/index";
    }

    @PostMapping
    public String sendSms(@RequestParam String recipient, @RequestParam String message, Model model) {
        String response = smsApiService.sendSms(recipient, message);

        model.addAttribute("response", response);

        return "manage/sms/index";
    }

    @GetMapping("/balance")
    public String getBalance(Model model) {
        String response = smsApiService.getBalance();

        model.addAttribute("response", response);

        return "manage/sms/index";
    }
}
