package com.stomat.controllers.user;

import com.stomat.controllers.ControllerUtils;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.user.UserRepository;
import com.stomat.services.user.UserService;
import com.stomat.transfer.Create;
import com.stomat.transfer.user.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String registration() {
        return "user/registration";
    }

    @PostMapping("/registration")
    public String addUser(@Validated(Create.class) UserAccountDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "user/registration";
        }

        UserAccount userFromDB = userRepository.findByEmail(userDto.getEmail());//todo: create validator
        if (userFromDB != null) {
            model.addAttribute("emailError", "Sorry, but user exists with specified name!");
            return "user/registration";
        }

        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            model.addAttribute("repeatPasswordError", "Sorry, passwords are not the same!");
            return "user/registration";
        }

        userService.createUser(userDto);

        return "redirect:/login";
    }
}
