package com.appointment.feignclient;

import com.appointment.response.AppointmentResponse;
import com.appointment.dto.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "appointment-service", contextId = "appointmentBookingClient")
public interface AppointmentBookingClient {

    @PostMapping("/api/v1/appointments")
    AppointmentResponse bookAppointment(@RequestBody AppointmentDto request);
}
