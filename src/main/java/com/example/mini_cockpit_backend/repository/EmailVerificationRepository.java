package com.example.mini_cockpit_backend.repository;

import com.example.mini_cockpit_backend.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Integer> {

    Optional<EmailVerification> findByToken(String token);

}
