package com.example.mini_cockpit_backend.service.verify;
import com.example.mini_cockpit_backend.dto.auth.RegisterRequest;
import com.example.mini_cockpit_backend.entity.EmailVerification;
import com.example.mini_cockpit_backend.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender mailSender;

    @Override
    public void createVerificationToken(RegisterRequest registerRequest) {
        String token = UUID.randomUUID().toString();
        EmailVerification verificationToken = new EmailVerification();
        verificationToken.setToken(token);
        verificationToken.setEmail(registerRequest.getEmail());

        emailVerificationRepository.save(verificationToken);

        String verificationUrl = "http://localhost:5173/verify?token=" + token;
        sendVerificationEmail(registerRequest.getEmail(), verificationUrl);
    }

    @Override
    public void sendVerificationEmail(String email, String verificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setFrom("verification@matwn.dk");
        message.setText("Click the link to verify your email: " + verificationUrl);
        mailSender.send(message);
    }

    @Override
    public boolean canProceed(String email) {
        return email.endsWith("gmail.com"); //email.endsWith(System.getenv("EMAIL"));
    }

    @Override
    public Optional<EmailVerification> findByToken(String token) {
        return emailVerificationRepository.findByToken(token);
    }


}