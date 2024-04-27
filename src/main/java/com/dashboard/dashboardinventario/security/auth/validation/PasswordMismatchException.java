package com.dashboard.dashboardinventario.security.auth.validation;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
