package com.appointment.controller;

import com.appointment.feignclient.DoctorFeignClient;
import com.doctor.dto.DoctorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorProxyController {

    private final DoctorFeignClient doctorFeignClient;

    @GetMapping("/api/v1/doctors/specialization/{specialization}")
    public List<DoctorDto> getDoctorsBySpecialization(@PathVariable("specialization") String specialization) {
        return doctorFeignClient.getDoctorsBySpecialization(specialization);
    }
}