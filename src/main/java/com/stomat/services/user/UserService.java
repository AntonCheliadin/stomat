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
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailSender emailSender;

    @Value("${stomat.server.url}")
    private String serverUrl;

    @Value("${mail.enabled}")
    private boolean emailEnabled;

    public UserAccount create(UserAccountDto userDto) {
        var userAccount = new UserAccount();
        userAccount.setFirstName(userDto.getFirstName());
        userAccount.setLastName(userDto.getLastName());
        userAccount.setEmail(userDto.getEmail());
        userAccount.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userAccount.setRoles(Collections.singleton(Role.USER));

        this.processActivation(userAccount);

        return userRepository.save(userAccount);
    }

    public boolean activate(String activationCode) {
        var userAccount = userRepository.findByActivationCode(activationCode);

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

    /**
     * if mail is enabled (mail.enabled is true) then send activation code to user
     * else user will be activate by default
     *
     * @param userAccount User account that needs activation.
     */
    private void processActivation(UserAccount userAccount) {
        if (emailEnabled) {
            userAccount.setActive(false);
            userAccount.setActivationCode(UUID.randomUUID().toString());
            emailSender.send(userAccount.getEmail(), "Activate stomat account!", serverUrl + "/activate/" + userAccount.getActivationCode());
        } else {
            userAccount.setActive(true);
        }
    }
}
