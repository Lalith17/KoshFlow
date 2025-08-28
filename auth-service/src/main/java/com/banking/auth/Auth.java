package com.banking.auth;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "auth_users")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID authUserId;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
}
