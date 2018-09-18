package com.stomat.controllers.user;

import com.stomat.domain.user.UserAccount;
import com.stomat.repository.user.UserRepository;
import com.stomat.services.user.UserService;
import com.stomat.transfer.Create;
import com.stomat.transfer.user.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String registration(UserAccountDto userAccountDto) {
        return "user/registration";
    }

    @PostMapping("/registration")
    public String addUser(@Validated(Create.class) UserAccountDto userAccountDto, BindingResult bindingResult, Model model) {

//        if (userAccountDto.getEmail() != null) {
//            UserAccount userFromDB = userRepository.findByEmail(userAccountDto.getEmail());//todo: create validator
//            if (userFromDB != null) {
//                bindingResult.addError(new FieldError("userAccountDto", "email", "Sorry, but user exists with specified name!"));
//            }
//        }

        if (!userAccountDto.getPassword().equals(userAccountDto.getRepeatPassword())) {
            bindingResult.addError(new FieldError("userAccountDto", "repeatPassword", "Sorry, passwords are not the same!"));
        }

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        userService.create(userAccountDto);

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code) {
        userService.activate(code);

        return "redirect:/login";

    }
}
