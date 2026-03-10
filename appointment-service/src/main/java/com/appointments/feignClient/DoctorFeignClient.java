package com.appointments.feignClient;

import com.appointments.dto.DoctorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service", contextId = "doctorFeignClient")
public interface DoctorFeignClient {

    @GetMapping("/api/v1/doctors/id/{doctorId}")
    DoctorDto getDoctorById(@PathVariable("doctorId") String doctorId);
}