package com.stomat.repository.user;

import com.stomat.domain.user.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
public interface UserRepository extends CrudRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findByEmailAndIdIsNot(String email, Long id);

    UserAccount findByActivationCode(String activationCode);
}
