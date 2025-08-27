package com.banking.notification.dto;

public record NewUserCreated(
        String name,
        String email
) {
}
