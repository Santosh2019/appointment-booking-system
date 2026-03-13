package com.appointment.controller;

import com.appointment.dto.PatientDto;
import com.appointment.feignclient.PatientFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PatientController {
    private final PatientFeignClient patientFeignClient;

    @PostMapping("/register/patient")
    public String registerPatient(@ModelAttribute("patientDto") PatientDto patientDto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
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
}
