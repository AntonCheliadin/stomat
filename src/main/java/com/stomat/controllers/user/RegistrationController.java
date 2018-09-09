package com.stomat.controllers.user;

import com.stomat.domain.user.UserAccount;
import com.stomat.dto.UserAccountDto;
import com.stomat.repository.user.UserRepository;
import com.stomat.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String addUser(UserAccountDto userDto, Map<String, Object> model) {
        String message = "";

        if (userDto.getName().isEmpty() ||
                userDto.getEmail().isEmpty() ||
                userDto.getPassword().isEmpty() ||
                userDto.getRepeatPassword().isEmpty()) {
            message = "Fill all fields, please!";
        }

        UserAccount userFromDB = userRepository.findByName(userDto.getName());
        if (message.isEmpty() && userFromDB != null) {
            message = "Sorry, but user exists with specified name!";
        }

        if (message.isEmpty() && !userDto.getPassword().equals(userDto.getRepeatPassword())) {
            message = "Sorry, passwords are not the same!";
        }

        if (!message.isEmpty()) {
            model.put("message", message);
            return "user/registration";
        }

        userService.createUser(userDto);

        return "redirect:/login";
    }
}
