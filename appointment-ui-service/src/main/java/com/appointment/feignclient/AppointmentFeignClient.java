package com.appointment.feignclient;

import com.appointement.response.AppointmentResponse;
import com.appointment.dto.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "appointment-service"/*, url = "${appointment.service.url}"*/)
public interface AppointmentFeignClient {
    @PostMapping("/api/v1/appointments")
    AppointmentResponse bookAppointment(@RequestBody AppointmentDto request);
}
