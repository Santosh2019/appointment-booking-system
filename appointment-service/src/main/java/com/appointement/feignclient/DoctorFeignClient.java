package com.appointement.feignclient;

import com.appointement.dto.DoctorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "doctor-service", url = "http://localhost:9091")
public interface DoctorFeignClient {
    @GetMapping("/api/v1/doctors/specialization/{specialization}")
    List<DoctorDto> getDoctorsBySpecialization(@PathVariable("specialization") String specialization);


    @GetMapping("/api/v1/doctors/id/{doctorId}")
    DoctorDto getDoctorById(@PathVariable("doctorId") String doctorId);
}
