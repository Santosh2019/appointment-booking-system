package com.doctor.service;

import com.doctor.common.IdGenerator;
import com.doctor.dto.DoctorDto;
import com.doctor.entity.Doctor;
import com.doctor.exception.ResourceNotFoundException;
import com.doctor.repo.DoctorRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class DoctorServiceImpl implements DoctorServices {

    private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepo doctorRepo;
    private final ModelMapper modelMapper;
    private final IdGenerator idGenerator;

    @Override
    public DoctorDto addDoctor(DoctorDto doctorDto) {
        log.info("=== ADD DOCTOR REQUEST RECEIVED ===");
        log.info("Name: {}, Mobile: {}, Aadhaar: {}",
                doctorDto.getDoctorName(), doctorDto.getMobile(), doctorDto.getAadharCard());

        validateDoctor(doctorDto);

        Doctor doctorEntity = modelMapper.map(doctorDto, Doctor.class);
        if (doctorEntity.getDoctorId() == null || doctorEntity.getDoctorId().isEmpty()) {
            doctorEntity.setDoctorId(idGenerator.generateDoctorId());
        }
        Doctor savedDoctor = doctorRepo.save(doctorEntity);
        log.info("Doctor saved in DB with ID: {}", savedDoctor.getDoctorId());
        DoctorDto responseDto = modelMapper.map(savedDoctor, DoctorDto.class);
        log.info("MASKING AADHAAR BEFORE SENDING RESPONSE:");
        log.info("   Original Aadhaar : {}", savedDoctor.getAadharCard());
        log.info("   Masked Aadhaar   : {}", responseDto.getAadharCard());
        return responseDto;
    }

    @Override
    public DoctorDto getDetails(String aadharCard) throws ResourceNotFoundException {
        log.info("=== FETCH DOCTOR BY AADHAAR: {} ===", aadharCard);
        Doctor doctor = doctorRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with Aadhaar: " + aadharCard));
        DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
        log.info("MASKING AADHAAR IN GET RESPONSE:");
        log.info("DB Value: {}", doctor.getAadharCard());
        log.info("Sent Value: {}", dto.getAadharCard());
        return dto;
    }

    @Override
    public DoctorDto updateDetails(String aadharCard, DoctorDto doctorDto) throws ResourceNotFoundException {
        Doctor existing = doctorRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        modelMapper.map(doctorDto, existing);
        Doctor updated = doctorRepo.save(existing);
        return modelMapper.map(updated, DoctorDto.class);
    }

    @Override
    public void deleteDetails(String aadharCard) throws ResourceNotFoundException {
        Doctor doctor = doctorRepo.findByAadharCard(aadharCard)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        doctorRepo.delete(doctor);
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepo.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .peek(dto -> dto.setAadharCard(maskAadhar(dto.getAadharCard()))) // masking
                .toList();
    }

    @Override
    public DoctorDto getDoctorById(String doctorId) throws ResourceNotFoundException {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
        dto.setAadharCard(maskAadhar(dto.getAadharCard())); // masked
        return dto;
    }

    @Override
    @CircuitBreaker(name = "doctorService")
    public List<DoctorDto> getDoctorsBySpecialization(String specialization) {
        return doctorRepo.findBySpecializationContainingIgnoreCase(specialization).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .peek(dto -> dto.setAadharCard(maskAadhar(dto.getAadharCard()))) // Mask Aadhaar
                .toList();
    }

    private void validateDoctor(DoctorDto dto) {
        if (dto.getAadharCard() == null || dto.getAadharCard().trim().isEmpty())
            throw new IllegalArgumentException("Aadhaar is required");

        if (dto.getMobile() == null || dto.getMobile().trim().isEmpty()) {
            throw new IllegalArgumentException("Mobile Number is already exists");
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email Id already exists");
        }
        if (dto.getPanCard() == null || dto.getPanCard().trim().isEmpty()) {
            throw new IllegalArgumentException("PanCard Id already exists");
        }
        if (doctorRepo.findByAadharCard(dto.getAadharCard()).isPresent())
            throw new IllegalArgumentException("Aadhaar already exists");
        if (doctorRepo.findByMobile(dto.getMobile()).isPresent()) {
            throw new IllegalArgumentException("Mobile Number already exists");
        }
        if (doctorRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email Id already exists");
        }
        if (doctorRepo.findByPanCard(dto.getPanCard()).isPresent()) {
            throw new IllegalArgumentException("PanCard is already exists");
        }
    }

    // doctor-service → service/DoctorServiceImpl.java
    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) return "XXXX-XXXX-XXXX";
        return "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
    }
}