package com.stomat.services.user;

import com.stomat.domain.user.Role;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.user.UserRepository;
import com.stomat.services.email.EmailSender;
import com.stomat.transfer.user.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

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

    @Autowired
    private EmailSender emailSender;

    @Value("${stomat.server.url}")
    private String serverUrl;

    public UserAccount create(UserAccountDto userDto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(userDto.getFirstName());
        userAccount.setLastName(userDto.getLastName());
        userAccount.setEmail(userDto.getEmail());
        userAccount.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userAccount.setActive(false);
        userAccount.setActivationCode(UUID.randomUUID().toString());

        userAccount.setRoles(Collections.singleton(Role.USER));

        emailSender.send(userAccount.getEmail(), "Activate stomat account!", serverUrl + "/activate/" + userAccount.getActivationCode());

        return userRepository.save(userAccount);
    }

    public boolean activate(String activationCode) {
        UserAccount userAccount = userRepository.findByActivationCode(activationCode);

        if (userAccount != null) {
            userAccount.setActive(true);
            userAccount.setActivationDate(new Date());
            userAccount.setActivationCode(null);

            userRepository.save(userAccount);

            return true;
        } else {
            return false;
        }
    }
}
