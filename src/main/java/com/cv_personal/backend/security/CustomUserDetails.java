package com.cv_personal.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final Long personId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long personId) {
        super(username, password, authorities);
        this.personId = personId;
    }

    public Long getPersonId() {
        return personId;
    }
}
