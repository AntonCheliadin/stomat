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
     * A person who can manage doctors
     */
    MANAGER,

    DOCTOR,

    USER
}
