package com.patient.service;

import com.patient.dto.PatientDto;
import com.patient.entity.Patient;
import com.patient.exception.ResourceNotFoundException;
import com.patient.repo.PatientRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepo patientRepo;

    private ModelMapper modelMapper;

    public PatientServiceImpl(PatientRepo patientRepo, ModelMapper modelMapper) {
        this.patientRepo = patientRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PatientDto addPatient(PatientDto patientDto) throws ResourceNotFoundException {
        validatePatient(patientDto);
        Patient isSaved = modelMapper.map(patientDto, Patient.class);
        Patient save = patientRepo.save(isSaved);
        return modelMapper.map(save, PatientDto.class);
    }

    @Override
    public PatientDto updateDetails(String aadharCard) {
        return null;
    }

    @Override
    public Patient getDetails(String aadharCard) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void deleteDetails(String aadharCard) throws ResourceNotFoundException {

    }

    private void validatePatient(PatientDto patientDto) throws ResourceNotFoundException {

        if (patientDto.getAadharCard() == null || patientDto.getAadharCard().isEmpty()) {
            throw new ResourceNotFoundException("Patient Aadhar should not be null");
        }
        if (patientDto.getEmail() == null || patientDto.getEmail().isEmpty()) {
            throw new ResourceNotFoundException("Patient Email should not be null");
        }
        if (patientDto.getAadharCard() == null || patientDto.getAadharCard().isEmpty()) {
            throw new ResourceNotFoundException("Aadhaar should not be null");
        }
        if (patientDto.getMobile() == null || patientDto.getMobile().isEmpty()) {
            throw new ResourceNotFoundException("Mobile Number should not be null");
        }

        //checks duplicates
        if (patientRepo.findByFullName(patientDto.getFullName()).isPresent())
            throw new ResourceNotFoundException("Patient name already exists");

        if (patientRepo.findByMobile(patientDto.getMobile()).isPresent())
            throw new ResourceNotFoundException("Mobile number already exists");

        if (patientRepo.findByEmail(patientDto.getEmail()).isPresent())
            throw new ResourceNotFoundException("Email already exists");

        if (patientRepo.findByAadharCard(patientDto.getAadharCard()).isPresent())
            throw new ResourceNotFoundException("Aadhaar already exists");
    }
}
