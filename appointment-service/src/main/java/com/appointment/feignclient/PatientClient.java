package com.appointment.feignclient;

import com.patient.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-client", url = "${patient-service.url}")
public interface PatientClient {
    @GetMapping("/api/v1/patients/{aadharCard}")
    PatientDto getPatientByAadhar(@PathVariable String aadharCard);
}