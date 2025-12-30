package com.appointement.service;

import com.appointement.request.AppointmentRequest;
import com.appointement.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse bookAppointment(AppointmentRequest request);
    List<AppointmentResponse> getAppointmentsByPatientId(String patientId);
    List<AppointmentResponse> getAppointmentsByDoctorId(String doctorId);
}
