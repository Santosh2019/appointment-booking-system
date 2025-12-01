package com.appointement.repo;

import com.appointement.entity.Appointment;
import com.appointement.request.AppointmentRequest;
import com.appointement.response.AppointmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    // AppointmentResponse bookAppointment(AppointmentRequest request);

    List<AppointmentResponse> getAppointmentsByPatientId(String patientId);

    List<AppointmentResponse> getAppointmentsByDoctorId(String doctorId);

    List<Appointment> findByPatientId(String patientId);

    List<Appointment> findByDoctorId(String doctorId);

    boolean existsByPatientIdAndDoctorIdAndAppointmentDate(String patientId, String doctorId, LocalDateTime appointmentDate);
}

