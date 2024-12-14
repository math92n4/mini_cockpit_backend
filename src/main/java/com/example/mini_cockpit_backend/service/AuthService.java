package com.example.mini_cockpit_backend.service;

import com.example.mini_cockpit_backend.config.JwtService;
import com.example.mini_cockpit_backend.dto.AuthRequest;
import com.example.mini_cockpit_backend.dto.AuthRes;
import com.example.mini_cockpit_backend.dto.RegisterRequest;
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
