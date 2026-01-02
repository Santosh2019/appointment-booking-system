package com.appointement.feignclient;

import com.appointement.dto.PatientDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
//@Retry(name = "patient-service", fallbackMethod = "fallBackPatient")
public interface PatientFeignClient {

    @GetMapping("/api/v1/patients/{patientId}")
    PatientDto getPatientById(@PathVariable("patientId") String patientId);

    default PatientDto fallBackPatient(String patientId, Throwable t) {
        PatientDto patientDto = new PatientDto();
        patientDto.setPatientId("Temporary Not Available");
        patientDto.setAadharCard("Temporary Not Available");
        patientDto.setFullName("Temporary Not Available");
        return patientDto;
    }
}
