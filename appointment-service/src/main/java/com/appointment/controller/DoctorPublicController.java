package com.appointment.controller;

import com.appointment.feignclient.DoctorClient;
import com.appointment.response.AvailableSlotResponse;
import com.appointment.service.AppointmentServices;
import com.doctor.dto.DoctorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorPublicController {
    private final DoctorClient doctorClient;
    private final AppointmentServices appointmentService;

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> doctors = doctorClient.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<DoctorDto>> getDoctorsBySpecialization(
            @PathVariable String specialization) {

        List<DoctorDto> doctors = doctorClient.getDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{doctorId}/slots")
    public ResponseEntity<List<AvailableSlotResponse>> getAvailableSlots(
            @PathVariable String doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Tumhare Doctor service mein working hours assume karo (e.g. 10 AM to 6 PM)
        List<LocalTime> allSlots = generateTimeSlots(); // 30-min intervals
        // Jo slots already booked hain, unko filter kar do
        List<LocalTime> bookedSlots = appointmentService.getBookedSlotsForDoctor(doctorId, date);
        List<AvailableSlotResponse> availableSlots = allSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .map(slot -> new AvailableSlotResponse(slot, true, "Available"))
                .toList();
        return ResponseEntity.ok(availableSlots);
    }

    private List<LocalTime> generateTimeSlots() {
        return List.of(
                LocalTime.of(10, 0), LocalTime.of(10, 30),
                LocalTime.of(11, 0), LocalTime.of(11, 30),
                LocalTime.of(12, 0), LocalTime.of(12, 30),
                LocalTime.of(13, 0), LocalTime.of(13, 30),
                LocalTime.of(14, 0), LocalTime.of(14, 30),
                LocalTime.of(15, 0), LocalTime.of(15, 30),
                LocalTime.of(16, 0), LocalTime.of(16, 30),
                LocalTime.of(17, 0), LocalTime.of(17, 30)
        );
    }
}
