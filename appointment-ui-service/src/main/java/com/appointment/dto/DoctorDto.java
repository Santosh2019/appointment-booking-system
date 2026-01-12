package com.appointment.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private String doctorName;
    private String mobile;
    private String email;
    private String aadharCard;
    private String panCard;
    private LocalDate dateOfBirth;
    private String gender;
    private Integer experience;
    private String qualification;
    private String specialization;
}