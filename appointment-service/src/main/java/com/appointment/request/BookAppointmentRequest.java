package com.appointment.request;
// src/main/java/com/appointment/dto/BookAppointmentRequest.java

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public record BookAppointmentRequest(

        @NotBlank(message = "Patient Aadhaar is required")
        @Pattern(regexp = "\\d{12}", message = "Patient Aadhaar must be 12 digits")
        String patientAadhar,

        @NotBlank(message = "Doctor Aadhaar is required")
        String docId,

        @NotNull(message = "Appointment date is required")
        @FutureOrPresent(message = "Appointment date cannot be in the past")
        LocalDate appointmentDate,

        @NotNull(message = "Appointment time is required")
        LocalTime appointmentTime,

        @Size(max = 500, message = "Reason should not exceed 500 characters")
        String reason
) {
}
