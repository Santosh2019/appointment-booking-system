package com.appointments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DoctorDto {
    private String doctorId;
    private String doctorName;
    private String specialization;
    private String mobile;
    private String aadharCard;
}
