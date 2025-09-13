package com.littleneighbors.shared.exceptions;

public class UserAlreadyHasFamily extends RuntimeException {
    public UserAlreadyHasFamily(Long userId) {
        super("User with id " + userId + " already belongs to a family.");
    }
}
