package com.stomat.services.security;

import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    public boolean isAccessAllowed(UserAccount activeUser, Doctor doctor) {
        return doctor.getManagers().stream()
                .anyMatch(user -> user.getId().equals(activeUser.getId()));
    }
}
