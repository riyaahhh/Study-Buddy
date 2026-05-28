package com.studybuddy.studybuddy_backend.service;

import com.studybuddy.studybuddy_backend.config.JwtUtil;
import com.studybuddy.studybuddy_backend.dto.AuthResponse;
import com.studybuddy.studybuddy_backend.dto.LoginRequest;
import com.studybuddy.studybuddy_backend.dto.SignupRequest;
import com.studybuddy.studybuddy_backend.model.User;
import com.studybuddy.studybuddy_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest request) {

        // 1. check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // 2. build the user object
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .isActive(true)
                .build();

        // 3. save to database
        User savedUser = userRepository.save(user);

        // 4. return response
        return new AuthResponse("Signup successful", savedUser.getId().toString(),null);
    }
    public AuthResponse login(LoginRequest request) {

    // 1. find user by email
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

    // 2. check password matches
    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
        throw new RuntimeException("Invalid email or password");
    }

    // 3. generate JWT token
    String token = jwtUtil.generateToken(user.getId());

    // 4. return response with token
    return new AuthResponse("Login successful", user.getId().toString(), token);
}
}