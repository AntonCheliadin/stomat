package com.stomat.api.user.auth;

import com.stomat.domain.user.Role;
import com.stomat.domain.user.UserAccount;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Set<Role> roles = Arrays.stream(customUser.roles().split(","))
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        UserAccount principal =
                new UserAccount() {{
                    setId(Long.parseLong(customUser.id()));
                    setFirstName(customUser.firstName());
                    setLastName(customUser.lastName());
                    setEmail(customUser.email());
                    setPassword(customUser.password());
                    setActive(Boolean.parseBoolean(customUser.active()));
                    setRoles(roles);
                }};
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

}
