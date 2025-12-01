package com.appointement.dto;

import lombok.Data;

@Data
public class DoctorDto {
    private String doctorId;        // doctor-service वापरत असलेलं id field
    private String doctorName;
    private String specialization;
    private String mobile;
    private String aadharCard;
}
