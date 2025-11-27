package com.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentDto(
        String appointmentId,
        String patientId,
        String patientName,
        String patientMobile,
        String doctorId,
        String doctorName,
        String doctorSpecialization,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        String status,
        String reason,
        LocalDateTime createdAt
) {
}