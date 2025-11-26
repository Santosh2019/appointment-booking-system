package com.patient.config;

import com.patient.dto.PatientDto;
import com.patient.entity.Patient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setSkipNullEnabled(true)                    // For partial updates
                .setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.typeMap(PatientDto.class, Patient.class)
                .addMappings(m -> {
                    //    m.skip(Patient::setPatientId);
                    //  m.skip(Patient::setAadharCard);
                    // m.skip(Patient::setFullName);
                    //m.skip(Patient::setDateOfBirth);
                    //m.skip(Patient::setGender);
                });
        return mapper;
    }
}