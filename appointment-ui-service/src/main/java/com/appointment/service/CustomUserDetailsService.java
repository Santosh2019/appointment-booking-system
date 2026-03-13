package com.appointment.service;

import com.appointment.dto.PatientDto;
import com.appointment.feignclient.DoctorFeignClient;
import com.appointment.feignclient.PatientFeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PatientFeignClient patientFeignClient;

    private final DoctorFeignClient doctorFeignClient;

    public CustomUserDetailsService(PatientFeignClient patientFeignClient, DoctorFeignClient doctorFeignClient) {
        this.patientFeignClient = patientFeignClient;
        this.doctorFeignClient = doctorFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            // check doctor
            var doctor = doctorFeignClient.doctorByEmail(username);
            if (doctor != null) {

                String dob = doctor.getDateOfBirth() != null
                        ? doctor.getDateOfBirth().toString()
                        : "";
                return org.springframework.security.core.userdetails.User
                        .withUsername(doctor.getEmail())
                        .password(dob)
                        .authorities("ROLE_DOCTOR")
                        .build();
            }

        } catch (Exception ignored) {
        }
        try {

            // check patient
            PatientDto patient = patientFeignClient.findByEmail(username);

            if (patient != null) {

                String dob = patient.getDateOfBirth() != null
                        ? patient.getDateOfBirth().toString()
                        : "";

                return org.springframework.security.core.userdetails.User
                        .withUsername(patient.getEmail())
                        .password(dob)
                        .authorities("ROLE_PATIENT")
                        .build();
            }

        } catch (Exception ignored) {
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}