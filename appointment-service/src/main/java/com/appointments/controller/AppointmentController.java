package com.appointments.controller;

import com.appointments.request.AppointmentRequest;
import com.appointments.response.AppointmentResponse;
import com.appointments.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AppointmentResponse> book(@Valid @RequestBody AppointmentRequest request) {
        log.info("Received appointment booking request for patient: {}", request.getPatientId());

        AppointmentResponse response = appointmentService.bookAppointment(request);

        log.info("Appointment successfully booked with ID: {}", response.getAppointmentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('PATIENT') and #patientId == authentication.name or hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<List<AppointmentResponse>> getByPatientId(@PathVariable("patientId") String patientId) {
        log.debug("Fetching appointments for patient: {}", patientId);
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        log.info("Fetching appointments for patient  vikram.desai90@gmail.com: {}", patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR') and #doctorId == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentResponse>> getByDoctorId(@PathVariable("doctorId") String doctorId) {
        log.debug("Fetching appointments for doctor: {}", doctorId);
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }
}