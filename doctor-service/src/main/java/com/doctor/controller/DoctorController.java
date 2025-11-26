package com.doctor.controller;

import com.doctor.dto.DoctorDto;
import com.doctor.exception.ResourceNotFoundException;
import com.doctor.service.DoctorServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private static final Logger log = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorServices doctorServices;

    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(@Valid @RequestBody DoctorDto doctorDto) throws ResourceNotFoundException {
        log.info("Creating new doctor → {}", doctorDto.getDoctorName());
        DoctorDto savedDoctor = doctorServices.addDoctor(doctorDto);
        savedDoctor.setAadharCard(maskAadhar(savedDoctor.getAadharCard()));
        log.info("Doctor created successfully | Aadhaar ending → {}",
                maskAadhar(savedDoctor.getAadharCard()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    @GetMapping("/{aadharCard}")
    public ResponseEntity<DoctorDto> getDoctor(@PathVariable String aadharCard) throws ResourceNotFoundException {
        log.info("Fetching doctor by Aadhaar ending → {}", maskAadhar(aadharCard));
        DoctorDto doctor = doctorServices.getDetails(aadharCard);
        doctor.setAadharCard(maskAadhar(doctor.getAadharCard())); // Mask here
        return ResponseEntity.ok(doctor);
    }

    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) return "XXXX-XXXX-XXXX";
        return "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
    }
}