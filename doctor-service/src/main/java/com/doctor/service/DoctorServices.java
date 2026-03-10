package com.doctor.service;

import com.doctor.dto.DoctorDto;
import com.doctor.exception.ResourceNotFoundException;

import java.util.List;

public interface DoctorServices {
    public DoctorDto addDoctor(DoctorDto doctorDto) throws ResourceNotFoundException;

    public DoctorDto updateDetails(String aadharCard, DoctorDto doctorDto) throws ResourceNotFoundException;

    public DoctorDto getDetails(String aadharCard) throws ResourceNotFoundException;

    public void deleteDetails(String aadharCard) throws ResourceNotFoundException;

    List<DoctorDto> getAllDoctors();

    DoctorDto getDoctorById(String doctorId) throws ResourceNotFoundException;

    List<DoctorDto> getDoctorsBySpecialization(String specialization);
}
