package com.stomat.validation.common;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Anton Chelyadin.
 * @since 24.12.18.
 */
public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatchConstraint, Object> {

    private String field;
    private String fieldMatch;

    public void initialize(FieldsValueMatchConstraint constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(fieldMatch);


        boolean isValid;

        if (fieldValue != null) {
            isValid = fieldValue.equals(fieldMatchValue);
        } else {
            isValid = fieldMatchValue == null;
        }

        if (!isValid) {
            context
                    .buildConstraintViolationWithTemplate("Fields are not equals.")
                    .addNode(field).addConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Fields are not equals.")
                    .addNode(fieldMatch).addConstraintViolation();
        }

        return isValid;
    }
}
