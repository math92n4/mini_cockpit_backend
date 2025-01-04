package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.dto.auth.AuthRequest;
import com.example.mini_cockpit_backend.dto.auth.AuthRes;
import com.example.mini_cockpit_backend.dto.auth.PasswordChangeDTO;
import com.example.mini_cockpit_backend.dto.auth.RegisterRequest;
import com.example.mini_cockpit_backend.dto.Response;
import com.example.mini_cockpit_backend.service.auth.AuthService;
import com.example.mini_cockpit_backend.service.verify.EmailVerificationService;
import com.example.mini_cockpit_backend.service.verify.EmailVerificationServiceImpl;
import com.example.mini_cockpit_backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/mini/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;


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

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String header) {

        try {
            String token = header.replace("Bearer: ", "");
            boolean isValid = authService.validateToken(token);

            if(isValid) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<AuthRes> updateUserWithToken(@RequestHeader("Authorization") String token,
                                                       @RequestBody PasswordChangeDTO passwordChangeDTO) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            AuthRes authRes = authService.updateUser(token, passwordChangeDTO);
            return new ResponseEntity<>(authRes, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
