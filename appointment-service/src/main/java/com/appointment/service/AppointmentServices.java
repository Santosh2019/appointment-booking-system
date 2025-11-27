package com.appointment.service;

import com.appointment.dto.AppointmentDto;
import com.appointment.request.BookAppointmentRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentServices {
    AppointmentDto bookAppointment(BookAppointmentRequest request);

    List<AppointmentDto> getAppointByPatient(String aadharCard);

    List<AppointmentDto> getAppointmentsByDoctor(String doctorAadhar);

    List<AppointmentDto> getDoctorAppointmentsToday(String doctorAadhar);

    AppointmentDto updateAppointmentStatus(String appointmentId, String status);

    void cancelAppointment(String appointmentId, String cancelledBy);

    public List<LocalTime> getBookedSlotsForDoctor(String doctorId, LocalDate date);
}
