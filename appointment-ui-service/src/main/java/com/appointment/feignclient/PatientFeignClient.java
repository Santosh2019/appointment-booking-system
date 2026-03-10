package com.appointment.feignclient;

import com.appointment.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "patient-service",
        contextId = "patientFeignClient"  // Unique
)
public interface PatientFeignClient {

    @GetMapping("/api/v1/patients/{patientId}")
    PatientDto getPatientById(@PathVariable("patientId") String patientId);

    @PostMapping("/api/v1/patients")
    PatientDto addPatient(@RequestBody PatientDto patientDto);

    @GetMapping("/api/v1/patients/email/{email}")
    PatientDto findByEmail(@PathVariable("email") String email);  // Fixed annotation

    // Fallbacks
    default PatientDto fallbackPatientById(String patientId, Throwable t) {
        System.out.println("Patient by ID fallback: " + t.getMessage());
        PatientDto dto = new PatientDto();
        dto.setPatientId(patientId);
        dto.setFullName("Service Unavailable");
        return dto;
    }
}