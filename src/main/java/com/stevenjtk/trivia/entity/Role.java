package com.stevenjtk.trivia.entity;

public enum Role {
    ADMIN("ADMIN: CAN_CRUD"),
    USER("USER: CAN_READ");

    private final String permissions;

    Role(String permissions) {
        this.permissions = permissions;
    }
}
