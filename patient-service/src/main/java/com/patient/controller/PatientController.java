package com.patient.controller;

import com.patient.ApplicationConstants.ApplicationConstants;
import com.patient.dto.PatientDto;
import com.patient.exception.ResourceNotFoundException;
import com.patient.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody PatientDto patientDto) throws ResourceNotFoundException {

        patientService.addPatient(patientDto);
        return new ResponseEntity(ApplicationConstants.DOCTOR_SAVED, HttpStatus.CREATED);
    }

}
