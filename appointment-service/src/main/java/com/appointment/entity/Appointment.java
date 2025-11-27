package com.appointment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String appointmentId;

    private String patientId;
    private String doctorId;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String status; // PENDING, CONFIRMED, CANCELLED, COMPLETED

    private String reason;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    private String patientName;
    private String doctorName;
    private String patientMobile;
    private String doctorSpecialization;
}