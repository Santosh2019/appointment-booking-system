package com.appointment.config;

import com.appointment.dto.DoctorDto;
import com.appointment.dto.PatientDto;
import com.appointment.feignclient.DoctorFeignClient;
import com.appointment.feignclient.PatientFeignClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    private final PatientFeignClient patientFeignClient;
    private final DoctorFeignClient doctorFeignClient;

    public CustomAuthenticationSuccessHandler(PatientFeignClient patientFeignClient,
                                              DoctorFeignClient doctorFeignClient) {
        this.patientFeignClient = patientFeignClient;
        this.doctorFeignClient = doctorFeignClient;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        String email = authentication.getName();
        HttpSession session = request.getSession();

        log.info("Login attempt for email: {}", email);

        try {

            DoctorDto doctor = doctorFeignClient.doctorByEmail(email);

            if (doctor != null) {

                session.setAttribute("loggedInDoctorId", doctor.getDoctorId());
                session.setAttribute("loggedInDoctorName", doctor.getDoctorName());
                session.setAttribute("loggedInDoctorQualification", doctor.getQualification());

                log.info("Doctor login successful: {}", doctor.getDoctorId());

                response.sendRedirect("/appointments/doctor");
                return;
            }

        } catch (Exception ex) {
            log.warn("Doctor login attempt failed for email: {}", email);
        }

        try {

            PatientDto patient = patientFeignClient.findByEmail(email);

            if (patient != null) {

                session.setAttribute("loggedInPatientId", patient.getPatientId());
                session.setAttribute("loggedInPatientName", patient.getFullName());

                log.info("Patient login successful: {}", patient.getPatientId());

                response.sendRedirect("/dashboard");
                return;
            }

        } catch (Exception ex) {
            log.warn("Patient login attempt failed for email: {}", email);
        }

        log.warn("Login failed for email: {}", email);
        response.sendRedirect("/auth/login?error=true");
    }
}