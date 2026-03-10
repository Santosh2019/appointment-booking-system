package com.appointment.service;

import com.appointment.dto.PatientDto;
import com.appointment.feignclient.PatientFeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PatientFeignClient patientFeignClient;

    public CustomUserDetailsService(PatientFeignClient patientFeignClient) {
        this.patientFeignClient = patientFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PatientDto patient = patientFeignClient.findByEmail(username);
        if (patient == null) {
            System.out.println("Patient not found for email: " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        System.out.println("Patient found: " + patient.getEmail() + ", DOB: " + patient.getDateOfBirth());
        String dobStr = patient.getDateOfBirth() != null ? patient.getDateOfBirth().toString() : "";

        return org.springframework.security.core.userdetails.User
                .withUsername(patient.getEmail())
                .password(dobStr)
                .authorities("ROLE_PATIENT")
                .build();
    }
}