package com.stomat.api.user.auth;

import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class, setupBefore = TestExecutionEvent.TEST_EXECUTION)
public @interface WithMockCustomUser {

    String id() default "1";

    String firstName() default "firstName";

    String lastName() default "lastName";

    String email() default "email@email.com";

    String password() default "password";

    String active() default "true";

    String roles() default "USER";
}
