package com.appointments.feignClient;

import com.appointments.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service", contextId = "patientFeignClientInAppointment")
public interface PatientFeignClient {

    @GetMapping("/api/v1/patients/{patientId}")
    PatientDto getPatientById(@PathVariable("patientId") String patientId);
}
