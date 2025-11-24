package com.patient.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Getter
@Setter
@ToString
public class PatientDto {
    private String patientId;
    private String fullName;
    private String mobile;
    private String email;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String aadharCard;
}
