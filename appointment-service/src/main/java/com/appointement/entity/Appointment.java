package com.appointement.entity;

import com.appointement.common.IdGenerator;
import com.appointement.dto.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "appointments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctorId", "appointmentDate"})
})
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

    public Appointment() {
        this.createdAt = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    private String reason;
    private LocalDateTime createdAt = LocalDateTime.now();

    public void generateId(IdGenerator idGen) {
        if (this.appointmentId == null || this.appointmentId.isEmpty()) {
            this.appointmentId = idGen.generateAppointmentId();
        }
    }
}