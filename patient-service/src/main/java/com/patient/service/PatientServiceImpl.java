package com.patient.service;

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

    @Override
    public PatientDto addPatient(PatientDto patientDto) {
        logger.info("Attempting to register new patient with Aadhar: {}", patientDto.getAadharCard());
        validatePatient(patientDto);                                      // Validate required fields & uniqueness
        Patient patientEntity = modelMapper.map(patientDto, Patient.class);
        Patient savedPatient = patientRepo.save(patientEntity);
        logger.info("Patient registered successfully | ID: {} | Aadhar: {}",
                savedPatient.getPatientId(), savedPatient.getAadharCard());
        return modelMapper.map(savedPatient, PatientDto.class);
    }

    @Override
    public PatientDto updateDetails(String aadharCard, PatientDto patientDto) throws ResourceNotFoundException {
        logger.info("Updating patient details for Aadhar Card: {}", aadharCard);

        Patient existingPatient = patientRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> {
                    logger.warn("Update failed — Patient not found with Aadhar: {}", aadharCard);
                    return new ResourceNotFoundException("Patient not found with Aadhar Card: " + aadharCard);
                });
        modelMapper.map(patientDto, existingPatient);
        Patient updatedPatient = patientRepo.save(existingPatient);
        logger.info("Patient details updated successfully for Aadhar: {}", aadharCard);
        return modelMapper.map(updatedPatient, PatientDto.class);
    }

    @Override
    public PatientDto getDetails(String aadharCard) throws ResourceNotFoundException {
        logger.info("Fetching patient details for Aadhar Card: {}", aadharCard);
        Patient patient = patientRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> {
                    logger.warn("Patient not found with Aadhar Card: {}", aadharCard);
                    return new ResourceNotFoundException("Patient not found with Aadhar Card: " + aadharCard);
                });
        logger.info("Patient details retrieved successfully for Aadhar: {}", aadharCard);
        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    public void deleteDetails(String aadharCard) throws ResourceNotFoundException {
        logger.info("Attempting to delete patient with Aadhar Card: {}", aadharCard);

        Patient patient = patientRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> {
                    logger.warn("Delete failed — Patient not found with Aadhar: {}", aadharCard);
                    return new ResourceNotFoundException("Patient not found with Aadhar Card: " + aadharCard);
                });

        patientRepo.delete(patient);
        logger.info("Patient deleted successfully | Aadhar: {}", aadharCard);
    }

    @Override
    public PatientDto activatePatient(String aadharCard) throws ResourceNotFoundException {
        // 1. Patient शोधा (Active/Inactive दोन्ही)
        Patient patient = patientRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with Aadhar: " + maskAadhar(aadharCard)));

        // 2. Already active असेल तर error द्या (optional – चांगली practice)
        if (patient.isActive()) {
            throw new DuplicateResourceException(
                    "Patient is already active | Aadhar ending: " + maskAadhar(aadharCard));
        }
        patient.setActive(true);
        Patient savedPatient = patientRepo.save(patient);

        logger.info("Patient activated successfully | Aadhar ending: {}", maskAadhar(aadharCard));

        PatientDto dto = modelMapper.map(savedPatient, PatientDto.class);

        dto.setAadharCard(maskAadhar(dto.getAadharCard()));

        return dto;
    }

    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) return "XXXX-XXXX-XXXX";
        return "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
    }

    /*Validates required fields and checks for duplicates during patient creation*/
    private void validatePatient(PatientDto patientDto) {
        logger.debug("Validating patient data for Aadhar: {}", patientDto.getAadharCard());

        if (patientDto.getAadharCard() == null || patientDto.getAadharCard().trim().isEmpty()) {
            throw new DuplicateResourceException("Aadhar Card is required");
        }
        if (patientDto.getEmail() == null || patientDto.getEmail().trim().isEmpty()) {
            throw new DuplicateResourceException("Email is required");
        }
        if (patientDto.getMobile() == null || patientDto.getMobile().trim().isEmpty()) {
            throw new DuplicateResourceException("Mobile number is required");
        }

        // Duplicate checks
        if (patientRepo.findByAadharCard(patientDto.getAadharCard()).isPresent()) {
            logger.warn("Registration blocked — Duplicate Aadhar Card: {}", patientDto.getAadharCard());
            throw new DuplicateResourceException("Aadhar Card already exists");
        }
        if (patientRepo.findByEmail(patientDto.getEmail()).isPresent()) {
            logger.warn("Registration blocked — Duplicate email: {}", patientDto.getEmail());
            throw new DuplicateResourceException("Email already in use");
        }
        if (patientRepo.findByMobile(patientDto.getMobile()).isPresent()) {
            logger.warn("Registration blocked — Duplicate mobile: {}", patientDto.getMobile());
            throw new DuplicateResourceException("Mobile number already registered");
        }
    }
}