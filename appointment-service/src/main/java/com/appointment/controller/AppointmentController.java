package com.appointment.controller;

import com.appointment.dto.AppointmentDto;
import com.appointment.request.BookAppointmentRequest;
import com.appointment.service.AppointmentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/appointments")
public class AppointmentController {
    private final AppointmentServices appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDto> book(@RequestBody BookAppointmentRequest req) {
        AppointmentDto dto = appointmentService.bookAppointment(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
