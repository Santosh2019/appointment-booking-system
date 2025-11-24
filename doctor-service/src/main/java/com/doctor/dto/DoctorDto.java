package com.doctor.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorDto {
    private String doctorName;
    private String mobile;
    private String emailId;
    private String aadharCard;
    private String panCard;
    private LocalDate dateOfBirth;
    private String gender;
    private String yearOfExperience;
    private String qualification;
    private String specialization;
}
