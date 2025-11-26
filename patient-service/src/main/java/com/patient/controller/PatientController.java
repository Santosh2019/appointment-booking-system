package com.patient.controller;

import com.patient.dto.PatientDto;
import com.patient.exception.ResourceNotFoundException;
import com.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")  // plural is REST standard
@RequiredArgsConstructor
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDto> savePatient(@Valid @RequestBody PatientDto patientDto) throws ResourceNotFoundException {
        logger.debug("POST /api/v1/patients → Creating patient | Aadhar: {}", maskAadhar(patientDto.getAadharCard()));
        PatientDto saved = patientService.addPatient(patientDto);
        logger.debug("Patient created successfully | Aadhar ending: {}", maskAadhar(saved.getAadharCard()));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{aadharCard}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable String aadharCard) throws ResourceNotFoundException {
        logger.debug("GET /api/v1/patients/{} → Fetching patient", maskAadhar(aadharCard));
        PatientDto patient = patientService.getDetails(aadharCard);
        logger.debug("Patient fetched successfully | Aadhar ending: {}", maskAadhar(aadharCard));
        return ResponseEntity.ok(patient);
    }

    @PatchMapping("/{aadharCard}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable String aadharCard,
            @RequestBody PatientDto patientDto) throws ResourceNotFoundException {

        logger.debug("PATCH /api/v1/patients/{} → Partial update | Mobile: {}, Email: {}",
                maskAadhar(aadharCard),
                patientDto.getMobile(),
                patientDto.getEmail());
        PatientDto updated = patientService.updateDetails(aadharCard, patientDto);
        logger.debug("Patient updated successfully | Aadhar ending: {}", maskAadhar(aadharCard));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{aadharCard}")
    public ResponseEntity<Void> deletePatient(@PathVariable String aadharCard) throws ResourceNotFoundException {
        logger.debug("DELETE /api/v1/patients/{} → Deleting patient", maskAadhar(aadharCard));
        patientService.deleteDetails(aadharCard);
        logger.debug("Patient deleted successfully | Aadhar ending: {}", maskAadhar(aadharCard));
        return ResponseEntity.noContent().build();
    }

    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) return "XXXX";
        return "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
    }
}