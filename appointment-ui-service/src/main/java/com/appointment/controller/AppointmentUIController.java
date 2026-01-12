package com.appointment.controller;

import com.appointment.feignclient.DoctorFeignClient;
import com.appointment.response.AppointmentResponse;
import com.appointment.dto.AppointmentDto;
import com.appointment.dto.DoctorDto;
import com.appointment.dto.PatientDto;
import com.appointment.feignclient.AppointmentBookingClient;
import com.appointment.feignclient.PatientFeignClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AppointmentUIController {

    private final PatientFeignClient patientFeignClient;
    private final AppointmentBookingClient appointmentFeignClient;
    private final DoctorFeignClient doctorFeignClient;

    // ────────────────────────────────────────────────
    // Patient Registration
    // ────────────────────────────────────────────────

    @GetMapping("/register/patient")
    public String showPatientRegistration(Model model) {
        model.addAttribute("patientDto", new PatientDto());
        return "register-patient";
    }

    @PostMapping("/register/patient")
    public String registerPatient(@ModelAttribute("patientDto") PatientDto patientDto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        // If you still want to keep basic validation (optional)
        if (result.hasErrors()) {
            return "register-patient";
        }

        try {
            patientFeignClient.addPatient(patientDto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Patient registered successfully! You can now login or go to dashboard.");
            return "redirect:/success-page";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed. Please try again.");
            return "register-patient";
        }
    }

    @GetMapping("/success-page")
    public String showSuccessPage() {
        return "success-page";
    }

    // ────────────────────────────────────────────────
    // Doctor Registration
    // ────────────────────────────────────────────────

    @GetMapping("/register/doctor")
    public String showDoctorRegistrationForm(Model model) {
        if (!model.containsAttribute("doctorDto")) {
            model.addAttribute("doctorDto", new DoctorDto());
        }
        return "register-doctor";
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(
            @ModelAttribute("doctorDto") DoctorDto doctorDto,
            RedirectAttributes redirectAttributes) {

        try {
            doctorFeignClient.addDoctor(doctorDto);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Doctor registered successfully! Your profile is now in the system.");
            redirectAttributes.addFlashAttribute("successTitle", "Doctor Registration Complete");

            return "redirect:/doctor-registration-success";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Registration failed: " + e.getMessage());
            redirectAttributes.addFlashAttribute("doctorDto", doctorDto);
            return "redirect:/register/doctor";
        }
    }


    // ────────────────────────────────────────────────
    // Login & Dashboard
    // ────────────────────────────────────────────────

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    // ────────────────────────────────────────────────
    // Appointment Booking
    // ────────────────────────────────────────────────

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

    @GetMapping("/appointments/success")
    public String showAppointmentSuccessPage() {
        return "appointment-success";
    }


    @GetMapping("/doctor-registration-success")
    public String showDoctorSuccessPage() {
        return "doctor-registration-success";  // → create templates/doctor-registration-success.html
    }
}