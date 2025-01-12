package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.dto.Response;
import com.example.mini_cockpit_backend.entity.EmailVerification;
import com.example.mini_cockpit_backend.entity.User;
import com.example.mini_cockpit_backend.entity.UserStatus;
import com.example.mini_cockpit_backend.service.verify.EmailVerificationService;
import com.example.mini_cockpit_backend.service.verify.EmailVerificationServiceImpl;
import com.example.mini_cockpit_backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "https://matwn.dk")
@RestController
@RequestMapping("/api/mini/verify")
@RequiredArgsConstructor
public class VerificationController {

    private final EmailVerificationService emailVerificationService;
    private final UserService userService;


    @GetMapping("")
    public ResponseEntity<Response> verifyEmail(@RequestParam("token") String token) {
        Optional<EmailVerification> verificationToken = emailVerificationService.findByToken(token);

        if (verificationToken.isEmpty()) {
            Response response = new Response(400, "Ugyldig token");
            return ResponseEntity.badRequest().body(response);
        }

        EmailVerification tokenEntity = verificationToken.get();

        if (tokenEntity.isExpired()) {
            emailVerificationService.handleExpiredToken(token);
            Response response = new Response(400, "Token er udløbet");
            return ResponseEntity.badRequest().body(response);
        }

        User user = userService.findByEmail(tokenEntity.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Bruger ikke fundet"));

        if(user.getUserStatus() == UserStatus.ACITVE) {
            return ResponseEntity.ok().body(new Response(200, "Allerede verificeret"));
        }

        user.setUserStatus(UserStatus.ACITVE);
        userService.save(user);

        Response response = new Response(200, "Bruger er nu verificeret");
        emailVerificationService.deleteById(tokenEntity.getId());

        return ResponseEntity.badRequest().body(response);
    }
}
