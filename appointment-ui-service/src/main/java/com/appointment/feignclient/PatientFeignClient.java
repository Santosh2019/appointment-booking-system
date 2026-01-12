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

    // Optional fallback
    default PatientDto fallBackPatient(String patientId, Throwable t) {
        PatientDto patientDto = new PatientDto();
        patientDto.setPatientId("Temporary Not Available");
        patientDto.setAadharCard("Temporary Not Available");
        patientDto.setFullName("Temporary Not Available");
        return patientDto;
    }
}