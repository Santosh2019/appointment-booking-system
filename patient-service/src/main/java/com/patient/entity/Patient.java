package com.patient.entity;

import com.patient.common.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "patients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "aadharCard"),
                @UniqueConstraint(columnNames = "mobile"),
                @UniqueConstraint(columnNames = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"patientId"})
public class Patient {
    @Id
    @Column(name = "patient_id", length = 20, updatable = false, nullable = false)
    private String patientId;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Autowired
    private transient IdGenerator idGenerator;

    @Column(nullable = false, unique = true, length = 10)
    private String mobile;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 10)
    private String gender;

    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String state;

    @Column(length = 6)
    private String pincode;

    @Column(nullable = false, unique = true, length = 12)
    private String aadharCard;

    /*@Column(name = "isActive", nullable = false)
    private Boolean isActive = false;
*/
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
