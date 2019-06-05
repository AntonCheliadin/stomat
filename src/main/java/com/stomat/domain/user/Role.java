package com.stomat.domain.user;

/**
 * @author Anton Chelyadin.
 * @since 24.06.18.
 */
public enum Role {

    /**
     * Admin of the service (the highest role)
     */
    ADMIN,

    /**
     * A person who can manage doctors
     */
    MANAGER,

    DOCTOR,

    USER
}
