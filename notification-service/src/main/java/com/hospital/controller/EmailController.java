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
        String name = (String) request.get("name");
        String patientId = (String) request.get("patientId");
        String mobileNo = (String) request.get("mobileNo");
        if (to == null || to.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email 'to' is required");
        }
        emailService.sendRegistrationEmail(to, name, patientId, mobileNo);
        return ResponseEntity.ok("Welcome email sent successfully to " + to);
    }
}