package com.hospital.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.mail.from:no-reply@appointment-system.com}")
    private String fromEmail;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            Context context = new Context();
            context.setVariables(variables);
            String htmlContent = templateEngine.process(templateName, context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);

            System.out.println("Email sent to: " + to);

        } catch (Exception e) {
            System.err.println("Email failed: " + to);
            e.printStackTrace();
        }
    }


    @Async
    public void sendRegistrationEmail(String to, String name, String patientId, String mobileNo) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("greeting", "Dear " + name + ",");
        variables.put("name", name);
        variables.put("email", to);
        variables.put("patientId", patientId);
        variables.put("registrationDate", java.time.LocalDate.now().toString());
        variables.put("loginLink", "http://localhost:9096/auth/login");

        sendEmail(to, "Registration Successful", "patient-email", variables);
    }


    @Async
    public void sendToDoctor(String to, String doctorName, String patientName, String dateTime) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("greeting", "Dear Dr. " + doctorName);
        variables.put("doctorName", doctorName);
        variables.put("patientName", patientName);
        variables.put("appointmentDate", dateTime);

        sendEmail(to, "New Appointment Assigned", "doctor-email", variables);
    }

    @Async
    public void sendToAdmin(String to, String subject, Map<String, Object> variables) {
        sendEmail(to, subject, "admin-email", variables);
    }
}