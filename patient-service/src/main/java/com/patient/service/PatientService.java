package com.patient.service;


import com.patient.dto.PatientDto;
import com.patient.entity.Patient;
import com.patient.exception.ResourceNotFoundException;

public interface PatientService {

    public PatientDto addPatient(PatientDto patientDto) throws ResourceNotFoundException;

    public PatientDto updateDetails(String aadharCard);

    public Patient getDetails(String aadharCard) throws ResourceNotFoundException;

    public void deleteDetails(String aadharCard) throws ResourceNotFoundException;

}
