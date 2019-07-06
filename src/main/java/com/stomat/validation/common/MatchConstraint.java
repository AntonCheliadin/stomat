package com.stomat.validation.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Chelyadin.
 * @since 24.12.18.
 */
@Constraint(validatedBy = MatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchConstraint {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "{match.fields.constraint}";

    String mainField();

    String matchField();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        MatchConstraint[] value();
    }
}
