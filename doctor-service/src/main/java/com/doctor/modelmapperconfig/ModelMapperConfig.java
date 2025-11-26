package com.doctor.modelmapperconfig;

import com.doctor.dto.DoctorDto;
import com.doctor.entity.Doctor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private static final Logger log = LoggerFactory.getLogger(ModelMapperConfig.class);

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(Doctor.class, DoctorDto.class)
                .addMappings(mapping -> {
                    mapping.skip(DoctorDto::setAadharCard);
                    mapping.map(
                            src -> maskAadhar(src.getAadharCard()),
                            DoctorDto::setAadharCard
                    );
                });
        log.info("ModelMapper ready → Aadhaar will be masked automatically");
        return mapper;
    }

    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) return "XXXX-XXXX-XXXX";
        String masked = "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
        log.info("Masking Aadhaar: {} → {}", aadhar, masked);
        return masked;
    }
}