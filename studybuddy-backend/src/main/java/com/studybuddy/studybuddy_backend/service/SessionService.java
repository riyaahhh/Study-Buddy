package com.studybuddy.studybuddy_backend.service;

import com.studybuddy.studybuddy_backend.dto.CreateSessionRequest;
import com.studybuddy.studybuddy_backend.dto.SessionResponse;
import com.studybuddy.studybuddy_backend.model.StudySession;
import com.studybuddy.studybuddy_backend.model.User;
import com.studybuddy.studybuddy_backend.repository.SessionRepository;
import com.studybuddy.studybuddy_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    // create a new session
    public SessionResponse createSession(UUID hostId, CreateSessionRequest request) {

        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudySession session = StudySession.builder()
                .title(request.getTitle())
                .subject(request.getSubject())
                .description(request.getDescription())
                .host(host)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .locationName(request.getLocationName())
                .maxMembers(request.getMaxMembers() != null ? request.getMaxMembers() : 10)
                .status("upcoming")
                .scheduledAt(request.getScheduledAt())
                .build();

        StudySession saved = sessionRepository.save(session);
        return mapToResponse(saved);
    }

    // get all active/upcoming sessions
    public List<SessionResponse> getAllSessions() {
        return sessionRepository.findByStatus("upcoming")
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // get single session by id
    public SessionResponse getSessionById(UUID sessionId) {
        StudySession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return mapToResponse(session);
    }

    // convert entity to response DTO
    private SessionResponse mapToResponse(StudySession session) {
        return SessionResponse.builder()
                .id(session.getId())
                .title(session.getTitle())
                .subject(session.getSubject())
                .description(session.getDescription())
                .hostId(session.getHost().getId())
                .hostName(session.getHost().getName())
                .latitude(session.getLatitude())
                .longitude(session.getLongitude())
                .locationName(session.getLocationName())
                .maxMembers(session.getMaxMembers())
                .status(session.getStatus())
                .scheduledAt(session.getScheduledAt())
                .createdAt(session.getCreatedAt())
                .build();
    }
}