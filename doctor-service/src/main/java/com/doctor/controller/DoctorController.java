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

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        log.info("Fetching all doctors list for patient app");
        List<DoctorDto> doctors = doctorServices.getAllDoctors();
        doctors.forEach(doc -> doc.setAadharCard(maskAadhar(doc.getAadharCard())));
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<DoctorDto>> getDoctorsBySpecialization(@PathVariable("specialization") String specialization) {
        log.info("Fetching doctors by specialization: {}", specialization);
        List<DoctorDto> doctors = doctorServices.getDoctorsBySpecialization(specialization);
        doctors.forEach(doc -> doc.setAadharCard(maskAadhar(doc.getAadharCard())));
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/aadhar/{aadharCard}")
    public ResponseEntity<DoctorDto> getDoctorByAadharCard(
            @PathVariable("aadharCard") String aadharCard) {
        log.info("Fetching doctor by Aadhaar ending → {}", maskAadhar(aadharCard));
        DoctorDto doctor = null;
        try {
            doctor = doctorServices.getDetails(aadharCard);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        doctor.setAadharCard(maskAadhar(doctor.getAadharCard()));
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/id/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable("doctorId") String doctorId) throws ResourceNotFoundException {
        DoctorDto doctor = doctorServices.getDoctorById(doctorId);
        return ResponseEntity.ok(doctor);
    }

    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) return "XXXX-XXXX-XXXX";
        return "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
    }
}