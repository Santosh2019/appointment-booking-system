package com.appointments.repo;

import com.appointments.entity.Appointment;
import com.appointments.request.AppointmentRequest;
import com.appointments.response.AppointmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    //AppointmentResponse bookAppointment(AppointmentRequest request);

    List<AppointmentResponse> getAppointmentsByPatientId(String patientId);

    List<AppointmentResponse> getAppointmentsByDoctorId(String doctorId);

    List<Appointment> findByPatientId(String patientId);

    List<Appointment> findByDoctorId(String doctorId);


}

