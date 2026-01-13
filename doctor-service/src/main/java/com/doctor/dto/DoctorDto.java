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
    @Size(min = 3, max = 50)
    private String doctorName;

    private String doctorId;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String mobile;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\d{12}$")
    private String aadharCard;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$")
    private String panCard;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    @Pattern(regexp = "Male|Female|Other")
    private String gender;

    // ← Change this from String to Integer
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 99, message = "Experience cannot exceed 99 years")
    private Integer experience;

    @NotBlank
    @Size(min = 2, max = 100)
    private String qualification;

    @NotBlank
    @Size(min = 3, max = 50)
    private String specialization;
}