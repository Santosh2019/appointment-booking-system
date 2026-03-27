package com.appointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        model.addAttribute("totalPatients", 120);
        model.addAttribute("totalDoctors", 45);
        model.addAttribute("totalAppointments", 320);

        return "admin-dashboard";
    }
}