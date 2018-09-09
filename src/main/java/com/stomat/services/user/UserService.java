package com.stomat.services.user;

import com.stomat.domain.user.Role;
import com.stomat.domain.user.UserAccount;
import com.stomat.transfer.user.UserAccountDto;
import com.stomat.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAccount createUser(UserAccountDto userDto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(userDto.getName());
        userAccount.setEmail(userDto.getEmail());
        userAccount.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userAccount.setActive(true);
        userAccount.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(userAccount);
    }

}
