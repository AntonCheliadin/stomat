package com.stomat.controllers.api.user;

import com.stomat.domain.user.UserAccount;
import com.stomat.services.user.UserService;
import com.stomat.transfer.Create;
import com.stomat.transfer.Update;
import com.stomat.transfer.user.UserAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity getUser(@AuthenticationPrincipal UserAccount userAccount) {
        if (userAccount == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userAccount);
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal UserAccount userAccount,
                                     @Validated(Update.class) @RequestBody UserAccountDto userAccountDto,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        userService.updateUser(userAccount, userAccountDto);

        return ResponseEntity.ok().build();
    }
}
