package com.stomat.validation.user;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Anton Chelyadin.
 * @since 18.09.18.
 */
@Documented
@Constraint(validatedBy = UniqueUserEmailValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserEmailConstraint {

    String message() default "{user.email.unique.error}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
