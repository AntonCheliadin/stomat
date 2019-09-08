package com.stomat.controllers.admin;

import com.stomat.domain.user.UserAccount;
import com.stomat.services.sms.SmsApiService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/sms")
public class SmsAdminController {

    private SmsApiService smsApiService;

    public SmsAdminController(SmsApiService smsApiService) {
        this.smsApiService = smsApiService;
    }

    @GetMapping
    public String getSmsPage(@AuthenticationPrincipal UserAccount user, Model model) {
        model.addAttribute("user", user);
        return "admin/sms/index";
    }

    @PostMapping
    public String sendSms(@RequestParam String recipient, @RequestParam String message, Model model) {
        String response = smsApiService.sendSms(recipient, message);

        model.addAttribute("response", response);

        return "admin/sms/index";
    }

    @GetMapping("/balance")
    public String getBalance(@AuthenticationPrincipal UserAccount user, Model model) {
        String response = smsApiService.getBalance();

        model.addAttribute("user", user);
        model.addAttribute("balanceResponse", response);

        return "admin/sms/index";
    }
}
