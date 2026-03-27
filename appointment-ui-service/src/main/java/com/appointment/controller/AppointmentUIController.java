package com.appointment.controller;

import com.appointment.dto.AppointmentDto;
import com.appointment.dto.DoctorDto;
import com.appointment.dto.PatientDto;
import com.appointment.feignclient.AppointmentFeignClient;
import com.appointment.feignclient.DoctorFeignClient;
import com.appointment.feignclient.PatientFeignClient;
import com.appointment.response.AppointmentResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class AppointmentUIController {

    private final PatientFeignClient patientFeignClient;
    private final AppointmentFeignClient appointmentFeignClient;
    private final DoctorFeignClient doctorFeignClient;

    @GetMapping("/register/patient")
    public String showPatientRegistration(Model model) {
        model.addAttribute("patientDto", new PatientDto());
        return "register-patient";
    }


    @GetMapping("/success-page")
    public String showSuccessPage() {
        return "success-page";
    }


    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    @GetMapping("/appointments/book")
    public String showBookAppointmentForm(Model model) {
        if (!model.containsAttribute("appointmentDto")) {
            model.addAttribute("appointmentDto", new AppointmentDto());
        }
        return "book-appointment";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(
            @ModelAttribute("appointmentDto") AppointmentDto appointmentDto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.appointmentDto", result);
            redirectAttributes.addFlashAttribute("appointmentDto", appointmentDto);
            return "redirect:/appointments/book";
        }
        try {
            AppointmentResponse response = appointmentFeignClient.bookAppointment(appointmentDto);
            redirectAttributes.addFlashAttribute("appointmentResponse", response);
            return "redirect:/appointments/success";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Booking failed. Please try again later.");
            redirectAttributes.addFlashAttribute("appointmentDto", appointmentDto);
            return "redirect:/appointments/book";
        }
    }

    @GetMapping("/appointments/my")
    public String viewMyAppointments(Model model, HttpSession session, Authentication authentication) {

        String patientId = (String) session.getAttribute("loggedInPatientId");
        String patientName = (String) session.getAttribute("loggedInPatientName");
        System.out.println("DEBUG /appointments/my - patientId from session: '" + patientId + "'");
        if (patientId == null || patientId.trim().isEmpty()) {
            System.out.println("DEBUG: Invalid patientId → redirecting to login");
            return "redirect:/auth/login";
        }

        List<AppointmentResponse> appointments =
                appointmentFeignClient.getAppointmentsByPatientId(patientId);

        System.out.println("DEBUG: Appointments list size: " +
                (appointments == null ? "null" : appointments.size()));
        for (AppointmentResponse appt : appointments) {
            if (appt.getAppointmentDate() != null) {
                LocalDateTime dt = appt.getAppointmentDate();
                appt.setDate(dt.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
                appt.setTime(dt.format(DateTimeFormatter.ofPattern("hh:mm a")));
                appt.setDay(dt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            } else {
                appt.setDate("—");
                appt.setTime("—");
                appt.setDay("—");
            }
        }

        model.addAttribute("appointments", appointments != null ? appointments : Collections.emptyList());
        model.addAttribute("patientId", patientId);
        model.addAttribute("patientName", patientName);

        return "my-appointments";
    }

    @PostMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable String id) {

        appointmentFeignClient.cancelAppointment(id);

        return "redirect:/appointments/my";
    }

    @ResponseBody
    @GetMapping("/api/v1/doctors/specialization/{specialization}")
    public ResponseEntity<List<DoctorDto>> getDoctors(
            @PathVariable("specialization") String specialization,
            @RequestParam(required = false, defaultValue = "true") boolean availableOnly) {
        try {
            List<DoctorDto> doctors = doctorFeignClient.getDoctorsBySpecialization(specialization);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Collections.emptyList());
        }
    }

    @GetMapping("/appointments/success")
    public String showAppointmentSuccessPage() {
        return "appointment-success";
    }


}

