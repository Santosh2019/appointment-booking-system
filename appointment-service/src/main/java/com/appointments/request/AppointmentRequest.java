package com.appointments.request;

import com.appointments.dto.AppointmentReason;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String patientId;
    @FutureOrPresent
    @NotNull
    private LocalDateTime appointmentDate;
    private String doctorId;

    private String specialization;
    @NotNull
    private AppointmentReason reason;
}