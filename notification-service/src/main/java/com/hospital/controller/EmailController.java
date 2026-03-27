package com.hospital.controller;

import com.hospital.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/patient/welcome")
    public ResponseEntity<String> sendPatientWelcomeEmail(@RequestBody Map<String, Object> request) {
        String to = (String) request.get("to");
        Map<String, Object> variables = (Map<String, Object>) request.get("variables");
        String name = (String) variables.get("name");
        String email = (String) variables.get("email");
        String patientId = (String) variables.get("patientId");
        String mobileNo = (String) variables.get("mobileNo");

        emailService.sendRegistrationEmail(to, name, patientId, mobileNo);
        return ResponseEntity.ok("Email sent successfully to patient");
    }
}