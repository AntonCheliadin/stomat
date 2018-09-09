package com.stomat.repository.user;

import com.stomat.domain.user.UserAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
public interface UserRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findByName(String name);
}
