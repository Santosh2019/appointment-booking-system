package com.appointments.common;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class IdGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // 0,O,1,I,l removed – clean look

    public String generatePatientId() {
        return "PAT-" + randomString(7);
    }

    public String generateDoctorId() {
        return "DOC-" + randomString(7);
    }

    public String generateAppointmentId() {
        return "APP-" + randomString(7);
    }

    private String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}