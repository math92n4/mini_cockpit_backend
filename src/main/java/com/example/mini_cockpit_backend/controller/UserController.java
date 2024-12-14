package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.dto.AuthRequest;
import com.example.mini_cockpit_backend.dto.AuthRes;
import com.example.mini_cockpit_backend.dto.RegisterRequest;
import com.example.mini_cockpit_backend.dto.Response;
import com.example.mini_cockpit_backend.entity.User;
import com.example.mini_cockpit_backend.entity.UserStatus;
import com.example.mini_cockpit_backend.service.AuthService;
import com.example.mini_cockpit_backend.service.EmailVerificationService;
import com.example.mini_cockpit_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest) {
        if (!emailVerificationService.canProceed(registerRequest.getEmail())) {
            return ResponseEntity.status(403).body(new Response(403, "Email not allowed"));
        }
        try {
            emailVerificationService.createVerificationToken(registerRequest);
            authService.register(registerRequest);
            return ResponseEntity.ok().body(new Response(200, "Registration mail sent to: " + registerRequest.getEmail()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.internalServerError().body(new Response(500, "An error occurred"));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthRes> register(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.authenticate(authRequest), HttpStatus.OK);
    }
}
