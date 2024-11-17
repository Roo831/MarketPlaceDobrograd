package com.poptsov.marketplace.database.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

admin, banned, user;


    @Override
    public String getAuthority() {
        return name();
    }
}
