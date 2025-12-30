package com.appointement.feignclient;

import com.appointement.dto.DoctorDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "doctor-service", fallbackFactory = DoctorClientFallbackFactory.class)
@CircuitBreaker(name = "doctorService")
//@Retry(name = "doctorService", fallbackMethod = "fallBackDoctor")
public interface DoctorFeignClient {
    Logger log = LoggerFactory.getLogger(DoctorFeignClient.class);

    @GetMapping("/api/v1/doctors/specialization/{specialization}")
    List<DoctorDto> getDoctorsBySpecialization(@PathVariable("specialization") String specialization);

    @GetMapping("/api/v1/doctors/id/{doctorId}")
    DoctorDto getDoctorById(@PathVariable("doctorId") String doctorId);

}
