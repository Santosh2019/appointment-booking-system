// File: src/main/java/com/patient/config/BeanConfig.java   (नाव बदला!)
package com.patient.ModelMapperConfig;

import com.patient.dto.PatientDto;
import com.patient.entity.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {   // नाव काहीही ठेवा – BeanConfig, AppConfig, MappingConfig

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(Patient.class, PatientDto.class)
                .addMappings(m -> m.map(
                        src -> maskAadhar(src.getAadharCard()),
                        PatientDto::setAadharCard
                ));
        return mapper;
    }

    private String maskAadhar(String aadhar) {
        if (aadhar == null || aadhar.length() < 4) return "XXXX-XXXX-XXXX";
        return "XXXX-XXXX-" + aadhar.substring(aadhar.length() - 4);
    }
}