package com.studybuddy.studybuddy_backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProfileRequest {
    private String name;
    private String bio;
    private String[] subjects;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String avatarUrl;
}