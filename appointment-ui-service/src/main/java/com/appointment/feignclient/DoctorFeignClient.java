package com.appointment.feignclient;

import com.appointment.dto.DoctorDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "doctor-service",
        contextId = "doctorFeignClient",  // Unique
        fallbackFactory = DoctorClientFallbackFactory.class
)
@CircuitBreaker(name = "doctorService")
public interface DoctorFeignClient {

    Logger log = LoggerFactory.getLogger(DoctorFeignClient.class);

    @GetMapping("/api/v1/doctors/specialization/{specialization}")
    List<DoctorDto> getDoctorsBySpecialization(@PathVariable("specialization") String specialization);

    @GetMapping("/api/v1/doctors/id/{doctorId}")
    DoctorDto getDoctorById(@PathVariable("doctorId") String doctorId);

    @PostMapping("/api/v1/doctors")
    DoctorDto addDoctor(@Valid @RequestBody DoctorDto doctorDto);
}