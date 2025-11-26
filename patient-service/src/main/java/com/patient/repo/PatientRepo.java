package com.patient.repo;

import com.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, String> {

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByMobile(String mobile);

    Optional<Patient> findByFullName(String fullName);

    Optional<Patient> findByAadharCard(String aadharCard);

    void deleteByAadharCard(String aadharCard);

}
