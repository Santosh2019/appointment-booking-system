package com.appointment.dto;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    @NotBlank(message = "PatientId is required")
    private String patientId;

    @NotBlank(message = "Patient full name is required")
    private String fullName;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Doctor selection is required")
    private String doctorId;

    @NotNull(message = "Appointment date & time is required")
    @FutureOrPresent(message = "Date must be today or in the future")
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Reason is required")
    private String reason;
}