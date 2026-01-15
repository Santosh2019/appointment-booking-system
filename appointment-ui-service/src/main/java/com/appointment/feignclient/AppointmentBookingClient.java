package com.appointment.feignclient;

import com.appointment.response.AppointmentResponse;
import com.appointment.dto.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "appointment-service", contextId = "appointmentBookingClient")
public interface AppointmentBookingClient {

    @PostMapping("/api/v1/appointments")
    AppointmentResponse bookAppointment(@RequestBody AppointmentDto request);

    @GetMapping("/api/v1/appointments/patient/{patientId}")
    List<AppointmentResponse> getAppointmentsByPatientId(@PathVariable("patientId") String patientId);
}
