package com.stomat.validation.user;

import com.stomat.domain.user.UserAccount;
import com.stomat.repository.user.UserRepository;
import com.stomat.transfer.user.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Anton Chelyadin.
 * @since 18.09.18.
 */
public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmailConstraint, UserAccountDto> {

    @Autowired
    private UserRepository userRepository;

    private String message;

    @Override
    public void initialize(UniqueUserEmailConstraint emailConstraint) {
        message = emailConstraint.message();
    }

    @Override
    public boolean isValid(UserAccountDto userAccountDto, ConstraintValidatorContext context) {
        boolean isValid;
        if (userAccountDto.getId() == null) {
            isValid = userRepository.findByEmail(userAccountDto.getEmail()).isEmpty();
        } else {
            isValid = userRepository.findByEmailAndIdIsNot(userAccountDto.getEmail(), userAccountDto.getId()).isEmpty();
        }

        if (!isValid) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("email").addConstraintViolation();
        }
        return isValid;
    }
}
