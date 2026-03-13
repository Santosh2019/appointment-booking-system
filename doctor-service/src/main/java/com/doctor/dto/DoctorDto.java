package com.doctor.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    @NotBlank(message = "Doctor name is mandatory")
    @Size(min = 3, max = 50, message = "Doctor name must be between 3 and 50 characters")
    private String doctorName;

    private String doctorId;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Enter valid 10 digit mobile number")
    private String mobile;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email address")
    private String email;

    @NotBlank(message = "Aadhaar number is required")
    @Pattern(regexp = "^\\d{12}$", message = "Aadhaar must be 12 digits")
    private String aadharCard;

    @NotBlank(message = "PAN number is required")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format")
    private String panCard;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Invalid gender selection")
    private String gender;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 99, message = "Experience cannot exceed 99 years")
    private Integer experience;

    @NotBlank(message = "Qualification is required")
    @Size(min = 2, max = 100, message = "Qualification must be between 2 and 100 characters")
    private String qualification;

    @NotBlank(message = "Specialization is required")
    @Size(min = 3, max = 50, message = "Specialization must be between 3 and 50 characters")
    private String specialization;
}