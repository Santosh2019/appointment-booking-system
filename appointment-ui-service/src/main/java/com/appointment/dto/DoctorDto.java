package com.appointment.dto;

import lombok.Data;

@Data
public class DoctorDto {
    private String id;            // doctorId (String असतो तुमच्याकडे)
    private String doctorName;
    private String specialization;
}