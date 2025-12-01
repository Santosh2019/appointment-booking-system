package com.doctor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "doctors",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"mobile", "email_id", "aadhar_card", "pan_card"})
        }
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Doctor {

    @Id
    @Column(name = "doctor_Id", length = 36, updatable = false, nullable = false)
    private String doctorId;
    @Column(name = "doctor_name", nullable = false, length = 50)
    private String doctorName;

    @Column(name = "mobile", nullable = false, length = 10, unique = true)
    private String mobile;

    @Column(name = "email_id", nullable = false, length = 100, unique = true)
    private String emailId;

    @Column(name = "aadhar_card", nullable = false, length = 12, unique = true)
    private String aadharCard;

    @Column(name = "pan_card", nullable = false, length = 10, unique = true)
    private String panCard;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "year_of_experience", nullable = false, length = 2)
    private String yearOfExperience;

    @Column(name = "qualification", nullable = false, length = 100)
    private String qualification;

    @Column(name = "specialization", nullable = false, length = 50)
    private String specialization;

    @PrePersist
    private void ensureId() {
        if (doctorId == null || doctorId.isBlank()) {
            doctorId = UUID.randomUUID().toString();
        }
    }

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;
}