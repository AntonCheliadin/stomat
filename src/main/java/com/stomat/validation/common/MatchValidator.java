package com.stomat.validation.common;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Anton Chelyadin.
 * @since 24.12.18.
 */
public class MatchValidator implements ConstraintValidator<MatchConstraint, Object> {

    private String mainField;
    private String matchField;
    private String message;

    public void initialize(MatchConstraint constraintAnnotation) {
        this.mainField = constraintAnnotation.mainField();
        this.matchField = constraintAnnotation.matchField();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {

        var fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(mainField);
        var fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(matchField);


        boolean isValid;

        if (fieldValue != null) {
            isValid = fieldValue.equals(fieldMatchValue);
        } else {
            isValid = fieldMatchValue == null;
        }

        if (!isValid) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(matchField)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
