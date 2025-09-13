package com.littleneighbors.features.user.entity;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

}