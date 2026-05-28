package com.studybuddy.studybuddy_backend.controller;

import com.studybuddy.studybuddy_backend.dto.CreateSessionRequest;
import com.studybuddy.studybuddy_backend.dto.SessionResponse;
import com.studybuddy.studybuddy_backend.service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionResponse> createSession(
            Authentication authentication,
            @Valid @RequestBody CreateSessionRequest request) {
        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(sessionService.createSession(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }
}
