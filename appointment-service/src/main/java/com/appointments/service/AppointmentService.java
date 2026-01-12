package com.appointments.service;

import com.appointments.request.AppointmentRequest;
import com.appointments.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse bookAppointment(AppointmentRequest request);

    List<AppointmentResponse> getAppointmentsByPatientId(String patientId);

    List<AppointmentResponse> getAppointmentsByDoctorId(String doctorId);
}
