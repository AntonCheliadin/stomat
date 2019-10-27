package com.stomat.controllers.admin;

import com.stomat.domain.user.UserAccount;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("**")
    public String index(@AuthenticationPrincipal UserAccount user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());

        return "admin/index";
    }
}
