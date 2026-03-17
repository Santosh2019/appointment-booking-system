package com.patient.service;

import com.patient.common.IdGenerator;
import com.patient.dto.PatientDto;
import com.patient.entity.Patient;
import com.patient.exception.DuplicateResourceException;
import com.patient.exception.ResourceNotFoundException;
import com.patient.repo.PatientRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepo patientRepo;
    private final ModelMapper modelMapper;
    private final IdGenerator idGenerator;

    @Override
    public PatientDto addPatient(PatientDto patientDto) {

        logger.info("Registering new patient");

        validatePatient(patientDto);

        Patient patientEntity = modelMapper.map(patientDto, Patient.class);

        if (patientEntity.getPatientId() == null || patientEntity.getPatientId().isEmpty()) {
            patientEntity.setPatientId(idGenerator.generatePatientId());
        }

        Patient savedPatient = patientRepo.save(patientEntity);

        logger.info("Patient registered successfully | ID: {}", savedPatient.getPatientId());

        return modelMapper.map(savedPatient, PatientDto.class);
    }

    @Override
    public PatientDto updateDetails(String aadharCard, PatientDto patientDto) throws ResourceNotFoundException {

        logger.info("Updating patient details");

        Patient existingPatient = patientRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with Aadhar ending: " + maskAadhar(aadharCard)));

        modelMapper.map(patientDto, existingPatient);

        Patient updatedPatient = patientRepo.save(existingPatient);

        logger.info("Patient updated successfully | ID: {}", updatedPatient.getPatientId());

        return modelMapper.map(updatedPatient, PatientDto.class);
    }

    @Override
    public PatientDto getDetails(String aadharCard) throws ResourceNotFoundException {

        logger.info("Fetching patient details");

        Patient patient = patientRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with Aadhar ending: " + maskAadhar(aadharCard)));

        logger.info("Patient details retrieved successfully | ID: {}", patient.getPatientId());

        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    public void deleteDetails(String aadharCard) throws ResourceNotFoundException {

        logger.info("Deleting patient");

        Patient patient = patientRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with Aadhar ending: " + maskAadhar(aadharCard)));

        patientRepo.delete(patient);

        logger.info("Patient deleted successfully | ID: {}", patient.getPatientId());
    }

    @Override
    public PatientDto activatePatient(String aadharCard) throws ResourceNotFoundException {

        logger.info("Activating patient");

        Patient patient = patientRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with Aadhar ending: " + maskAadhar(aadharCard)));


        Patient savedPatient = patientRepo.save(patient);

        logger.info("Patient activated successfully | ID: {}", savedPatient.getPatientId());

        return modelMapper.map(savedPatient, PatientDto.class);
    }

    @Override
    public PatientDto getPatientById(String patientId) throws ResourceNotFoundException {

        logger.debug("Fetching patient by ID: {}", patientId);

        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with ID: " + patientId));

        return modelMapper.map(patient, PatientDto.class);
    }


    @Override
    public PatientDto getPatientByEmail(String email) throws ResourceNotFoundException {
        logger.debug("Fetching patient by email");
        Patient patient = patientRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with email"));

        return modelMapper.map(patient, PatientDto.class);
    }


    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) return "XXXX-XXXX-XXXX";
        return "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
    }

    private void validatePatient(PatientDto patientDto) {
        logger.debug("Validating patient data");

        if (patientDto.getAadharCard() == null || patientDto.getAadharCard().trim().isEmpty()) {
            throw new IllegalArgumentException("Aadhar Card is required");
        }

        if (patientDto.getEmail() == null || patientDto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (patientDto.getMobile() == null || patientDto.getMobile().trim().isEmpty()) {
            throw new IllegalArgumentException("Mobile number is required");
        }

        if (patientRepo.findByAadharCard(patientDto.getAadharCard()).isPresent()) {
            throw new DuplicateResourceException("Aadhar Card already exists");
        }

        if (patientRepo.findByEmail(patientDto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already in use");
        }

        if (patientRepo.findByMobile(patientDto.getMobile()).isPresent()) {
            throw new DuplicateResourceException("Mobile number already registered");
        }
    }
}
