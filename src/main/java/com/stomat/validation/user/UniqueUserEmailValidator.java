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

    @Override
    public void initialize(UniqueUserEmailConstraint email) {
    }

    @Override
    public boolean isValid(UserAccountDto userAccountDto, ConstraintValidatorContext cxt) {
        Boolean isValid;
        if (userAccountDto.getId() == null) {
            UserAccount existedUser = userRepository.findByEmail(userAccountDto.getEmail());
            isValid = existedUser == null;
        } else {
            UserAccount existedUser = userRepository.findByEmailAndIdIsNot(userAccountDto.getEmail(), userAccountDto.getId());
            isValid = existedUser == null;
        }

        if (!isValid) {
            cxt
                    .buildConstraintViolationWithTemplate("Sorry, but user exists with specified email!")
                    .addNode("email").addConstraintViolation();
        }
        return isValid;
    }
}
