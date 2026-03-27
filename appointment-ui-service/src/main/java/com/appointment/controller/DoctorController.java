package com.appointment.controller;

import com.appointment.dto.DoctorDto;
import com.appointment.feignclient.AppointmentFeignClient;
import com.appointment.feignclient.DoctorFeignClient;
import com.appointment.response.AppointmentResponse;
import feign.FeignException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private static final Logger log = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorFeignClient doctorFeignClient;

    private final AppointmentFeignClient appointmentFeignClient;

    @GetMapping("/register/doctor")
    public String showDoctorRegistrationForm(Model model) {

        log.info("Opening doctor registration page");

        if (!model.containsAttribute("doctorDto")) {
            model.addAttribute("doctorDto", new DoctorDto());
        }

        return "register-doctor";
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(
            @Valid @ModelAttribute("doctorDto") DoctorDto doctorDto,
            BindingResult result,
            Model model) {

        log.info("Doctor registration request received for email: {}", doctorDto.getEmail());

        if (result.hasErrors()) {
            log.warn("UI validation failed for doctor registration");
            return "register-doctor";
        }

        try {

            log.info("Calling doctor-service to save doctor: {}", doctorDto.getDoctorName());

            doctorFeignClient.addDoctor(doctorDto);

            log.info("Doctor registered successfully: {}", doctorDto.getEmail());

            model.addAttribute("successMessage", "Doctor registered successfully");
            return "register-doctor";

        } catch (FeignException.BadRequest ex) {

            log.error("Validation error received from doctor-service");

            String response = ex.contentUTF8();
            log.error("Doctor-service validation response: {}", response);

            if (response.contains("panCard")) {
                result.rejectValue("panCard", "error.panCard", "Invalid PAN");
            }

            if (response.contains("mobile")) {
                result.rejectValue("mobile", "error.mobile", "Enter valid mobile number");
            }

            if (response.contains("aadharCard")) {
                result.rejectValue("aadharCard", "error.aadharCard", "Aadhaar must be 12 digits");
            }

            if (response.contains("gender")) {
                result.rejectValue("gender", "error.gender", "Invalid gender selection");
            }

            if (response.contains("experience")) {
                result.rejectValue("experience", "error.experience", "Experience is required");
            }

            return "register-doctor";
        }
    }

    @GetMapping("/appointments/doctor")
    public String showDoctorAppointments(Model model, HttpSession session) {

        String doctorId = (String) session.getAttribute("loggedInDoctorId");

        if (doctorId == null) {
            return "redirect:/auth/login";
        }

        List<AppointmentResponse> appointments =
                appointmentFeignClient.getAppointsByDoctorId(doctorId);

        model.addAttribute("appointments", appointments);

        return "doctor-appointments";
    }

    @GetMapping("/doctor-registration-success")
    public String showDoctorSuccessPage() {
        log.info("Doctor registration success page opened");
        return "doctor-registration-success";
    }
}