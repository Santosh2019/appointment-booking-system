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
}
