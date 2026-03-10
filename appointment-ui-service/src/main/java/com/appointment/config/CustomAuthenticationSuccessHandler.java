package com.appointment.config;

import com.appointment.dto.PatientDto;
import com.appointment.feignclient.PatientFeignClient;
import jakarta.servlet.ServletException;
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

    public CustomAuthenticationSuccessHandler(PatientFeignClient patientFeignClient) {
        this.patientFeignClient = patientFeignClient;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        log.info("Authentication success for email: {}", email);
        HttpSession session = request.getSession();
        try {
            PatientDto patient = patientFeignClient.findByEmail(email);
            if (patient != null) {
                session.setAttribute("loggedInPatientId", patient.getPatientId());
                session.setAttribute("loggedInPatientName", patient.getFullName());
                log.info("Session updated: patientId = {}, name = {}", patient.getPatientId(), patient.getFullName());
            } else {
                log.warn("No patient found for email: {}", email);
            }
        } catch (Exception e) {
            log.error("Failed to fetch patient after login for email: {}", email, e);
        }
        response.sendRedirect("/dashboard");
    }
}