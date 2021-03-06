package com.stomat.controllers.user;

import com.stomat.services.user.UserService;
import com.stomat.transfer.Create;
import com.stomat.transfer.user.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
@Controller
public class RegistrationController {

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping("/registration")
    public String registration(UserAccountDto userAccountDto) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addUser(@Validated(Create.class) UserAccountDto userAccountDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        userService.create(userAccountDto);

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code) {
        userService.activate(code);

        return "redirect:/account";
    }
}
