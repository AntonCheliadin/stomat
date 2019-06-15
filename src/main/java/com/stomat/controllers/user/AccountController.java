package com.stomat.controllers.user;

import com.stomat.domain.user.UserAccount;
import com.stomat.repository.user.UserRepository;
import com.stomat.services.user.UserService;
import com.stomat.transfer.Update;
import com.stomat.transfer.user.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/account")
@Controller
public class AccountController {

    @Autowired
    public AccountController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping()
    public String account(@AuthenticationPrincipal UserAccount user, Model model) {
        model.addAttribute("userAccountDto", user);

        return "account/account";
    }

    @PostMapping()
    public String updateAccount(@AuthenticationPrincipal UserAccount userAccount,
                                @Validated(Update.class) UserAccountDto userAccountDto, BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "account/account";
        }

        userService.updateUser(userAccount, userAccountDto);

        return "redirect:/account";
    }
}
