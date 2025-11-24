package com.doctor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "doctors")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

    @NotBlank(message = "Doctor name is mandatory")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String doctorName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String mobile;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String emailId;

    @NotBlank(message = "Aadhar card is mandatory")
    @Pattern(regexp = "^\\d{12}$", message = "Aadhar must be exactly 12 digits")
    @Column(unique = true)
    private String aadharCard;

    @NotBlank(message = "PAN card is required")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN card format")
    @Column(unique = true)
    private String panCard;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female or Other")
    private String gender;

    @NotBlank(message = "Years of experience is required")
    @Pattern(regexp = "\\d+", message = "Experience must be a number")
    @Size(max = 2, message = "Experience cannot exceed 99 years")
    private String yearOfExperience;

    @NotBlank(message = "Qualification is required")
    @Size(min = 2, max = 100)
    private String qualification;

    @NotBlank(message = "Specialization is mandatory")
    @Size(min = 3, max = 50)
    private String specialization;
}