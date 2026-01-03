package com.appointment.controller;

import com.appointement.response.AppointmentResponse;
import com.appointment.dto.AppointmentDto;
import com.appointment.feignclient.AppointmentFeignClient;
import com.appointment.feignclient.PatientFeignClient;
import com.patient.dto.PatientDto;
import jakarta.validation.Valid;
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
    private final AppointmentFeignClient appointmentFeignClient;

    // Patient Registration - Show form
    @GetMapping("/register/patient")
    public String showPatientRegistration(Model model) {
        model.addAttribute("patientDto", new PatientDto());
        return "register-patient";
    }

    // Patient Registration - Handle submission
    @PostMapping("/register/patient")
    public String registerPatient(@Valid @ModelAttribute("patientDto") PatientDto patientDto,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            return "register-patient";
        }
        try {
            patientFeignClient.addPatient(patientDto);
            return "redirect:/success-page";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed. Please try again.");
            return "register-patient";
        }
    }

    // Success page after registration
    @GetMapping("/success-page")
    public String showSuccessPage() {
        return "success-page";
    }

    // Login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Patient dashboard
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    // Show book appointment form
    @GetMapping("/appointments/book")
    public String showBookAppointmentForm(Model model) {
        if (!model.containsAttribute("appointmentDto")) {
            model.addAttribute("appointmentDto", new AppointmentDto());
        }
        return "book-appointment";
    }

    // Handle appointment booking submission
    @PostMapping("/appointments/book")
    public String bookAppointment(
            @Valid @ModelAttribute("appointmentDto") AppointmentDto appointmentDto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        // If validation errors → redirect back with form data preserved
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.appointmentDto", result);
            redirectAttributes.addFlashAttribute("appointmentDto", appointmentDto);
            return "redirect:/appointments/book";
        }

        try {
            // Call microservice to book appointment
            AppointmentResponse response = appointmentFeignClient.bookAppointment(appointmentDto);

            // Pass the full response to the success page via flash attribute
            redirectAttributes.addFlashAttribute("appointmentResponse", response);

            // Redirect to the detailed success page
            return "redirect:/appointments/success";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Booking failed. Please try again later.");
            redirectAttributes.addFlashAttribute("appointmentDto", appointmentDto);
            return "redirect:/appointments/book";
        }
    }

    // New: Dedicated success page after successful booking
    @GetMapping("/appointments/success")
    public String showAppointmentSuccessPage() {
        return "appointment-success"; // → templates/appointment-success.html
    }
}