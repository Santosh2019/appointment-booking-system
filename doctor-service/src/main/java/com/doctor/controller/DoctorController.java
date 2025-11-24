package com.doctor.controller;

import com.doctor.constants.ApplicationConstants;
import com.doctor.dto.DoctorDto;
import com.doctor.exception.ResourceNotFoundException;
import com.doctor.service.DoctorServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private DoctorServices doctorServices;

    public DoctorController(DoctorServices doctorServices) {
        this.doctorServices = doctorServices;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody DoctorDto doctorDto) throws ResourceNotFoundException {
        doctorServices.addDoctor(doctorDto);
        return new ResponseEntity(ApplicationConstants.DOCTOR_SAVED, HttpStatus.CREATED);
    }


}
