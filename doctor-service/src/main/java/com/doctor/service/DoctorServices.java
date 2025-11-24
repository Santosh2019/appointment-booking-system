package com.doctor.service;

import com.doctor.dto.DoctorDto;
import com.doctor.entity.Doctor;
import com.doctor.exception.ResourceNotFoundException;

public interface DoctorServices {
    public DoctorDto addDoctor(DoctorDto doctorDto) throws ResourceNotFoundException;

    public DoctorDto updateDetails(String aadharCard);

    public Doctor getDetails(String aadharCard) throws ResourceNotFoundException;

    public void deleteDetails(String aadharCard) throws ResourceNotFoundException;
}
