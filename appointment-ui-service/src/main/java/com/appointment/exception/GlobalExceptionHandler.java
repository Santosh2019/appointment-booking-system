package com.appointment.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("error", ex);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", 500);
        model.addAttribute("timestamp", java.time.LocalDateTime.now());
        return new ModelAndView("error");
    }
}