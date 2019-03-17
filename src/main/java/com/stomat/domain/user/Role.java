package com.stomat.domain.user;

/**
 * @author Anton Chelyadin.
 * @since 24.06.18.
 */
public enum Role {

    /**
     * Owner of the service (the highest role)
     */
    OWNER,

    /**
     * A person employed in an office to answer the telephone, deal with clients.
     */
    RECEPTIONIST,

    DOCTOR,

    USER,

    ADMIN
}
