package com.banking.auth;

import com.banking.auth.dto.LoginRequest;
import io.jsonwebtoken.JwtException;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthRepo authRepo;

    public AuthService(AuthRepo authRepo, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authRepo = authRepo;
    }

    public Optional<String> authenticate(LoginRequest loginRequestDTO) {
        return authRepo.findByEmail(loginRequestDTO.email())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.password(),
                        u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail()));
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e){
            return false;
        }
    }
}
