package com.appointement.controller;

import com.appointement.request.AppointmentRequest;
import com.appointement.response.AppointmentResponse;
import com.appointement.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AppointmentResponse> book(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointmentService.bookAppointment(request));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<AppointmentResponse>> getByPatientId(@PathVariable("patientId") String patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<List<AppointmentResponse>> getByDoctorId(@PathVariable String doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorId(doctorId));
    }

    @GetMapping("/hello")
    public String test() {
        return "Running";
    }

}