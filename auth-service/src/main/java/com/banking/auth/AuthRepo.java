package com.banking.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepo extends JpaRepository<Auth, UUID> {
    Optional<Auth> findByEmail(String email);
}
