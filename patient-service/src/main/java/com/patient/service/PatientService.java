package com.patient.service;


import com.patient.dto.PatientDto;
import com.patient.exception.ResourceNotFoundException;

public interface PatientService {
    public PatientDto addPatient(PatientDto patientDto) throws ResourceNotFoundException;

    public PatientDto updateDetails(String aadharCard, PatientDto patient) throws ResourceNotFoundException;

    public PatientDto getDetails(String aadharCard) throws ResourceNotFoundException;

    public void deleteDetails(String aadharCard) throws ResourceNotFoundException;

    PatientDto activatePatient(String aadharCard) throws ResourceNotFoundException;

    PatientDto getPatientById(String patientId);
}
