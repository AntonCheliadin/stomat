package com.stomat.common.integration.user;

import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockDbUserSecurityContextFactory.class, setupBefore = TestExecutionEvent.TEST_EXECUTION)
public @interface MockDbUser {

    String firstName() default "firstName";

    String lastName() default "lastName";

    String email() default "dbTestUser@email.com";

    String password() default "password";

    String active() default "true";

    String[] roles() default "USER";
}
