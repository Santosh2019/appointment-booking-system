package com.appointement.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    @NotBlank
    private String patientId;
    @FutureOrPresent
    @NotNull
    private LocalDateTime appointmentDate;
    private String doctorId;

    private String specialization;
    @NotBlank
    @Size(max = 200)
    private String reason;
}