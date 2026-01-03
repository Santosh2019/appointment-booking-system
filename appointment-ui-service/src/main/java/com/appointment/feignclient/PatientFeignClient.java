package com.appointment.feignclient;

import com.patient.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "patient-service")
public interface PatientFeignClient {
    @PostMapping("/api/v1/patients")
    PatientDto addPatient(@RequestBody PatientDto patientDto);

    @GetMapping("/api/v1/patients/email/{email}")
    PatientDto findByEmail(String email);
}
