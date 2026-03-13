package com.appointments.service;

import com.appointments.common.IdGenerator;
import com.appointments.dto.AppointmentStatus;
import com.appointments.dto.DoctorDto;
import com.appointments.dto.PatientDto;
import com.appointments.entity.Appointment;
import com.appointments.feignClient.DoctorFeignClient;
import com.appointments.feignClient.PatientFeignClient;
import com.appointments.repo.AppointmentRepository;
import com.appointments.request.AppointmentRequest;
import com.appointments.response.AppointmentResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository repository;
    private final IdGenerator idGenerator;
    private final PatientFeignClient patientFeignClient;
    private final DoctorFeignClient doctorFeignClient;

    private static final List<LocalTime> ALLOWED_TIMES = List.of(
            LocalTime.of(10, 0), LocalTime.of(10, 30),
            LocalTime.of(11, 0), LocalTime.of(11, 30),
            LocalTime.of(12, 0), LocalTime.of(12, 30),
            LocalTime.of(14, 0), LocalTime.of(14, 30),
            LocalTime.of(15, 0), LocalTime.of(15, 30),
            LocalTime.of(16, 0), LocalTime.of(16, 30),
            LocalTime.of(17, 0), LocalTime.of(17, 30),
            LocalTime.of(18, 0)
    );

    @Override
    public AppointmentResponse bookAppointment(AppointmentRequest request) {

        log.info("Booking appointment for patientId: {}, doctorId: {}, date: {}",
                request.getPatientId(), request.getDoctorId(), request.getAppointmentDate());

        validateTimeSlot(request.getAppointmentDate());


        PatientDto patient = patientFeignClient.getPatientById(request.getPatientId());

        if (patient == null || patient.getFullName() == null || patient.getFullName().trim().isEmpty()) {
            log.error("Patient not found: {}", request.getPatientId());
            throw new RuntimeException("Patient not found for ID: " + request.getPatientId());
        }

        DoctorDto doctor = doctorFeignClient.getDoctorById(request.getDoctorId());

        if (doctor == null || doctor.getDoctorName() == null || doctor.getDoctorName().trim().isEmpty()) {
            log.error("Doctor not found: {}", request.getDoctorId());
            throw new RuntimeException("Doctor not found for ID: " + request.getDoctorId());
        }

        Appointment appointment = new Appointment();

        appointment.generateId(idGenerator);
        appointment.setPatientId(request.getPatientId());
        appointment.setFullName(patient.getFullName());

        appointment.setDoctorId(request.getDoctorId());
        appointment.setDoctorName(doctor.getDoctorName());

        appointment.setSpecialization(
                doctor.getSpecialization() != null
                        ? doctor.getSpecialization()
                        : "General"
        );

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());

        appointment.setStatus(AppointmentStatus.SCHEDULED);


        Appointment saved = repository.save(appointment);

        log.info("Appointment booked successfully with ID: {}", saved.getAppointmentId());

        return buildAppointmentResponse(saved);
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByPatientId(String patientId) {
        log.info("Appointment Fetched successfully: {}", patientId);
        return repository.findByPatientId(patientId).stream()
                .map(this::buildAppointmentResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDoctorId(String doctorId) {
        List<Appointment> appointments = repository.findByDoctorId(doctorId);
        return appointments.stream()
                .map(this::buildAppointmentResponse)
                .toList();
    }

/*
    @Transactional
    public void confirmAppointment(String id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + id));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException(
                    "Cannot confirm appointment. Current status: " + appointment.getStatus()
            );
        }
        appointment.setStatus(AppointmentStatus.CONFIRMED);
    }*/


    @Override
    public void cancelAppointment(String appointmentId) {
        log.info("Cancelling appointment: {}", appointmentId);
        Appointment appt = repository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + appointmentId));
        appt.setStatus(AppointmentStatus.CANCELLED);
        repository.save(appt);
        log.info("Appointment cancelled successfully: {}", appointmentId);
    }

    private AppointmentResponse buildAppointmentResponse(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.setAppointmentId(a.getAppointmentId());
        r.setPatientId(a.getPatientId());
        r.setFullName(a.getFullName());
        r.setDoctorId(a.getDoctorId());
        r.setDoctorName(a.getDoctorName());
        r.setSpecialization(a.getSpecialization());
        r.setAppointmentDate(a.getAppointmentDate());
        r.setReason(a.getReason().toString());
        r.setStatus(a.getStatus());
        r.setCreatedAt(a.getCreatedAt());
        LocalDateTime dt = a.getAppointmentDate();
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH);
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");
        r.setDate(dt.format(dateFmt));
        r.setTime(dt.format(timeFmt));
        r.setDay(dt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        return r;
    }

    private void validateTimeSlot(LocalDateTime appointmentDateTime) {
        if (appointmentDateTime == null) {
            throw new IllegalArgumentException("Appointment date and time is required!");
        }
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book appointment in the past!");
        }
        if (appointmentDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Clinic is closed on Sundays!");
        }
        LocalTime time = appointmentDateTime.toLocalTime();
        if (!ALLOWED_TIMES.contains(time)) {
            throw new IllegalArgumentException("Invalid time slot. Allowed slots: 10:00 AM to 6:00 PM (30-minute intervals).");
        }
    }
}