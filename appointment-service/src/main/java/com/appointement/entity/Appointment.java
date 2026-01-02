package com.appointement.entity;

import com.appointement.common.IdGenerator;
import com.appointement.dto.AppointmentReason;
import com.appointement.dto.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Table(name = "appointments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctorId", "appointmentDate"})
})
@Data
public class Appointment {
    private String doctorName;
    @Id
    @Column(name = "appointment_id", length = 36, updatable = false, nullable = false)
    private String appointmentId;
    @Column(name = "patient_name")
    private String fullName;
    @Column(name = "doctor_id")
    private String doctorId;
    private String patientId;
    private LocalDateTime appointmentDate;
    private String specialization;

    private AppointmentStatus status;

    public Appointment() {
        this.createdAt = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    private AppointmentReason reason;
    private LocalDateTime createdAt = LocalDateTime.now();

    public void generateId(IdGenerator idGen) {
        if (this.appointmentId == null || this.appointmentId.isEmpty()) {
            this.appointmentId = idGen.generateAppointmentId();
        }
    }
}