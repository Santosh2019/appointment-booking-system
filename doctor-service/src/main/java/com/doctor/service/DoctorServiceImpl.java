package com.doctor.service;

import com.doctor.dto.DoctorDto;
import com.doctor.entity.Doctor;
import com.doctor.exception.ResourceNotFoundException;
import com.doctor.repo.DoctorRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorServices {

    private DoctorRepo doctorRepo;

    private ModelMapper modelMapper;

    public DoctorServiceImpl(DoctorRepo doctorRepo, ModelMapper modelMapper) {
        this.doctorRepo = doctorRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorDto addDoctor(DoctorDto doctorDto) throws ResourceNotFoundException {
        validateDoctor(doctorDto);
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
        Doctor save = doctorRepo.save(doctor);
        return modelMapper.map(save, DoctorDto.class);
    }

    @Override
    public DoctorDto updateDetails(String aadharCard) {
        return null;
    }

    @Override
    public Doctor getDetails(String aadharCard) throws ResourceNotFoundException {
        return doctorRepo.findByAadharCard(aadharCard).orElseThrow(() -> new ResourceNotFoundException(
                "Doctor Not Found with Aadhaar Card: " + aadharCard));
    }

    @Override
    public void deleteDetails(String aadharCard) throws ResourceNotFoundException {
        Doctor doctor = doctorRepo.findByAadharCard(aadharCard).orElseThrow(() -> new ResourceNotFoundException("Doctor Not found"));
        doctorRepo.delete(doctor);

    }

    private void validateDoctor(DoctorDto doctorDto) throws ResourceNotFoundException {
        if (doctorDto.getDoctorName() == null || doctorDto.getDoctorName().isEmpty()) {
            throw new ResourceNotFoundException("Doctor Name should not be null");
        }
        if (doctorDto.getMobile() == null || doctorDto.getMobile().isEmpty()) {
            throw new ResourceNotFoundException("Mobile Number should not be null");
        }
        if (doctorDto.getEmailId() == null || doctorDto.getEmailId().isEmpty()) {
            throw new ResourceNotFoundException("Email should not be null");
        }
        if (doctorDto.getAadharCard() == null || doctorDto.getAadharCard().isEmpty()) {
            throw new ResourceNotFoundException("Aadhaar should not be null");
        }
        if (doctorDto.getPanCard() == null || doctorDto.getPanCard().isEmpty()) {
            throw new ResourceNotFoundException("PAN should not be null");
        }

        if (doctorRepo.findByDoctorName(doctorDto.getDoctorName()).isPresent())
            throw new ResourceNotFoundException("Doctor name already exists");

        if (doctorRepo.findByMobile(doctorDto.getMobile()).isPresent())
            throw new ResourceNotFoundException("Mobile number already exists");

        if (doctorRepo.findByEmailId(doctorDto.getEmailId()).isPresent())
            throw new ResourceNotFoundException("Email already exists");

        if (doctorRepo.findByAadharCard(doctorDto.getAadharCard()).isPresent())
            throw new ResourceNotFoundException("Aadhaar already exists");

        if (doctorRepo.findByPanCard(doctorDto.getPanCard()).isPresent())
            throw new ResourceNotFoundException("PAN already exists");
    }
}


