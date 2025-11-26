package com.doctor.service;

import com.doctor.dto.DoctorDto;
import com.doctor.entity.Doctor;
import com.doctor.exception.ResourceNotFoundException;
import com.doctor.repo.DoctorRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class DoctorServiceImpl implements DoctorServices {

    private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepo doctorRepo;
    private final ModelMapper modelMapper;

    @Override
    public DoctorDto addDoctor(DoctorDto doctorDto) {
        log.info("=== ADD DOCTOR REQUEST RECEIVED ===");
        log.info("Name: {}, Mobile: {}, Aadhaar: {}",
                doctorDto.getDoctorName(), doctorDto.getMobile(), doctorDto.getAadharCard());

        validateDoctor(doctorDto);

        Doctor doctorEntity = modelMapper.map(doctorDto, Doctor.class);
        Doctor savedDoctor = doctorRepo.save(doctorEntity);
        log.info("Doctor saved in DB with ID: {}", savedDoctor.getDocId());
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
        log.info("   DB Value    : {}", doctor.getAadharCard());
        log.info("   Sent Value  : {}", dto.getAadharCard());
        log.info("=== DOCTOR DETAILS RETURNED ===\n");
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

    private void validateDoctor(DoctorDto dto) {
        if (dto.getAadharCard() == null || dto.getAadharCard().trim().isEmpty())
            throw new IllegalArgumentException("Aadhaar is required");
        if (doctorRepo.findByAadharCard(dto.getAadharCard()).isPresent())
            throw new IllegalArgumentException("Aadhaar already exists");
    }
}