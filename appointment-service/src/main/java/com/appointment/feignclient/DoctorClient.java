package com.appointment.feignclient;

import com.doctor.dto.DoctorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "doctor-client", url = "${doctor-service.url}")
public interface DoctorClient {
/*

    @GetMapping("/api/v1/doctors/{doctorId}")
    DoctorDto getDoctorById(@PathVariable("doctorId") String doctorId);
*/

    @GetMapping("/api/v1/doctors")
    List<DoctorDto> getAllDoctors();

    @GetMapping("/api/v1/doctors/id/{doctorId}")
    DoctorDto getDoctorById(@PathVariable("doctorId") String doctorId);

    @GetMapping("/api/v1/doctors/specialization/{specialization}")
    List<DoctorDto> getDoctorsBySpecialization(@PathVariable("specialization") String specialization);
}