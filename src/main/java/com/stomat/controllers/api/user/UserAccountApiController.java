package com.stomat.controllers.api.user;

import com.stomat.domain.user.UserAccount;
import com.stomat.services.user.UserService;
import com.stomat.transfer.user.UserAccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class UserAccountApiController {

    public UserAccountApiController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping()
    public UserAccount getUser(@AuthenticationPrincipal UserAccount userAccount) {
        return userAccount;
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateStudent(@AuthenticationPrincipal UserAccount userAccount,
                                                @Valid @RequestBody UserAccountDto userAccountDto,
                                                BindingResult bindingResult, Model model) {
//todo: handle userDto invalid, but validation doesn't executing
        userService.updateUser(userAccount, userAccountDto);

        return ResponseEntity.ok().build();
    }
}
