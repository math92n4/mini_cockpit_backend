package com.example.mini_cockpit_backend.service.auth;

import com.example.mini_cockpit_backend.config.JwtService;
import com.example.mini_cockpit_backend.dto.auth.AuthRequest;
import com.example.mini_cockpit_backend.dto.auth.AuthRes;
import com.example.mini_cockpit_backend.dto.auth.PasswordChangeDTO;
import com.example.mini_cockpit_backend.dto.auth.RegisterRequest;
import com.example.mini_cockpit_backend.entity.User;
import com.example.mini_cockpit_backend.entity.UserStatus;
import com.example.mini_cockpit_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserStatus(UserStatus.PENDING);
        user.setEmail(registerRequest.getEmail());
        user.setPw(passwordEncoder.encode(registerRequest.getPw()));
        userRepository.save(user);
    }

    public AuthRes authenticate(AuthRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPw()
        ));

        var user = userRepository.findByEmail(authenticationRequest.getEmail());

        User checkUser = user.get();

        if(checkUser.getUserStatus() == UserStatus.PENDING) {
            return null;
        }

        var jwtToken = jwtService.generateToken(user.get());

        return AuthRes.builder()
                .token(jwtToken)
                .email(checkUser.getEmail())
                .build();
    }

    public boolean validateToken(String token) {
        System.out.println(token);
        try {
            String extractedEmail = jwtService.extractUserName(token);
            Optional <User> optionalUser = userRepository.findByEmail(extractedEmail);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return jwtService.isTokenValid(token, user);
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public AuthRes updateUser(String token, PasswordChangeDTO passwordChangeDTO) {
        String email = "";
        User foundUser = null;

        if (token.startsWith("Bearer")) {
            email = jwtService.extractUserName(token.substring(7));
        }

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            foundUser = user.get();

            if (passwordEncoder.matches(passwordChangeDTO.getOldPassword(), foundUser.getPw())) {

                if (passwordChangeDTO.getNewPassword() != null && !passwordChangeDTO.getNewPassword().isEmpty()) {
                    foundUser.setPw(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));

                    userRepository.save(foundUser);

                } else {
                    throw new RuntimeException("Password is empty");
                }

            } else {
                throw new RuntimeException("Doesnt match");
            }

        } else {
            throw new RuntimeException("User not found");
        }

        var jwtToken = jwtService.generateToken(foundUser);

        return AuthRes.builder()
                .token(jwtToken)
                .email(foundUser.getEmail())
                .build();

    }
    /*
    public AuthRes updateUser(RegisterRequest updatedUser, String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        User foundUser = null;
        if (user.isPresent()) {
            foundUser = user.get();

            foundUser.setUsername(updatedUser.getUsername());
            if (!updatedUser.getPw().isEmpty()) {
                foundUser.setPw(passwordEncoder.encode(updatedUser.getPw()));
            }

            userRepository.save(foundUser);
        }

        var jwtToken = jwtService.generateToken(foundUser);

        return AuthRes.builder()
                .token(jwtToken)
                .build();
    }
    */
}
