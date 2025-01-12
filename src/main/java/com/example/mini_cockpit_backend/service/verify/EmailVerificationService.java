package com.example.mini_cockpit_backend.service.verify;

import com.example.mini_cockpit_backend.dto.auth.RegisterRequest;
import com.example.mini_cockpit_backend.entity.EmailVerification;

import java.util.Optional;

public interface EmailVerificationService {

    void createVerificationToken(RegisterRequest registerRequest);
    void sendVerificationEmail(String email, String verificationUrl);

    void handleExpiredToken(String token);
    boolean canProceed(String email);
    Optional<EmailVerification> findByToken(String token);
    void deleteById(int id);
}
