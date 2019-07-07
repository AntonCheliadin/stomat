package com.stomat.common.integration.user;

import com.stomat.domain.user.Role;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MockDbUserSecurityContextFactory
        implements WithSecurityContextFactory<MockDbUser> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(MockDbUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Set<Role> roles = Arrays.stream(customUser.roles())
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        UserAccount user = userRepository.findByEmail(customUser.email())
                .orElse(new UserAccount(customUser.email()));
        user.setFirstName(customUser.firstName());
        user.setLastName(customUser.lastName());
        user.setPassword(customUser.password());
        user.setActive(Boolean.parseBoolean(customUser.active()));
        user.setRoles(roles);

        UserAccount principal = userRepository.save(user);
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

}
