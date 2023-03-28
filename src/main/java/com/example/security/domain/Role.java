package com.example.security.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_VIEW_INFO,
    ROLE_VIEW_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
