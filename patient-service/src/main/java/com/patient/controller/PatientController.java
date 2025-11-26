package com.patient.controller;

import com.patient.dto.ActivationResponseDto;
import com.patient.dto.ApiResponse;
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
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDto> savePatient(@Valid @RequestBody PatientDto patientDto) throws ResourceNotFoundException {
        logger.debug("POST /api/v1/patients → Creating patient | Aadhar: {}", maskAadhar(patientDto.getAadharCard()));
        PatientDto saved = patientService.addPatient(patientDto);
        saved.setAadharCard(maskAadhar(saved.getAadharCard()));
        logger.debug("Patient created successfully | Aadhar ending: {}", maskAadhar(saved.getAadharCard()));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{aadharCard}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable String aadharCard) throws ResourceNotFoundException {
        logger.debug("GET /api/v1/patients/{} → Fetching patient", maskAadhar(aadharCard));
        PatientDto patient = patientService.getDetails(aadharCard);
        patient.setAadharCard(maskAadhar(patient.getAadharCard()));
        logger.debug("Patient fetched successfully | Aadhar ending: {}", maskAadhar(aadharCard));
        return ResponseEntity.ok(patient);
    }

    @PatchMapping("/{aadharCard}/activate")
    public ResponseEntity<ApiResponse<ActivationResponseDto>> activatePatient(
            @PathVariable String aadharCard) throws ResourceNotFoundException {
        PatientDto activated = patientService.activatePatient(aadharCard);

        ActivationResponseDto responseData = new ActivationResponseDto(
                activated.getFullName(),
                maskAadhar(activated.getAadharCard()),
                activated.getPatientId(),
                true
        );
        ApiResponse<ActivationResponseDto> response = new ApiResponse<>(
                "Patient account activated successfully",
                responseData
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{aadharCard}")
    public ResponseEntity<String> deletePatient(@PathVariable String aadharCard) throws ResourceNotFoundException {
        logger.debug("DELETE /api/v1/patients/{} → Deleting patient", maskAadhar(aadharCard));
        patientService.deleteDetails(aadharCard);
        logger.debug("Patient deleted successfully | Aadhar ending: {}", maskAadhar(aadharCard));
        return ResponseEntity.noContent().build();
    }


    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) {
            return "XXXX-XXXX-XXXX";
        }
        return "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
    }
}