package com.appointment.feignclient;

import com.doctor.dto.DoctorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "doctor-service"/*, url = "${doctor.service.url}"*/)
public interface DoctorFeignClient {

    @GetMapping("/api/v1/doctors/specialization/{specialization}")
    List<DoctorDto> getDoctorsBySpecialization(@PathVariable("specialization") String specialization);
}
