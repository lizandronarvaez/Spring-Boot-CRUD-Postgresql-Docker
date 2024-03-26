package com.dashboard.dashboardinventario.security.auth.validation;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String message) {
        super(message);
    }
}
