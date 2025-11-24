package com.patient.repo;

import com.patient.dto.PatientDto;
import com.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

    public Optional<PatientDto> findByEmail(String email);

    public Optional<PatientDto> findByMobile(String mobile);

    public Optional<PatientDto> findByaadharCard(String aadharCard);

}
