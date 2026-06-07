package com.studybuddy.studybuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String bio;
    private String[] subjects;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String avatarUrl;
    private BigDecimal reliabilityScore;
    private Integer totalJoined;
    private Integer totalCompleted;
}