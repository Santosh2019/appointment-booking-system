// File: src/main/java/com/patient/dto/ActivationResponseDto.java
package com.patient.dto;

import lombok.Data;

@Data
public class ActivationResponseDto {
    private String fullName;
    private String aadharCard;
    private String patientId;
    private String account;

    public ActivationResponseDto(String fullName, String aadharCard, String patientId, boolean isActive) {
        this.fullName = fullName;
        this.aadharCard = aadharCard;
        this.patientId = patientId;
        this.account = isActive ? "active" : "inactive";
    }
}