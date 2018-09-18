package com.stomat.validation.user;

import com.stomat.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Anton Chelyadin.
 * @since 18.09.18.
 */
public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmailConstraint, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUserEmailConstraint email) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return userRepository.findByEmail(email) == null;
    }
}
