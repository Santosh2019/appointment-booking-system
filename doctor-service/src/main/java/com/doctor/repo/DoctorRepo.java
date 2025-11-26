package com.doctor.repo;

import com.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByAadharCard(String aadharCard);

    Optional<Doctor> findByMobile(String mobile);

    Optional<Doctor> findByEmailId(String emailId);

    Optional<Doctor> findByPanCard(String panCard);

    Optional<Doctor> findByDoctorName(String doctorName);
}
