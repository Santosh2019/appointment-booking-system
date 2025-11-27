package com.appointment.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookAppointmentResponse(
        String appointmentId,
        String patientName,
        String doctorName,
        String doctorSpecialization,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        String status,
        String message
) {
}