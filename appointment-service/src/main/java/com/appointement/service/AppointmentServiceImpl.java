package com.appointement.service;

import com.appointement.common.IdGenerator;
import com.appointement.dto.AppointmentReason;
import com.appointement.dto.AppointmentStatus;
import com.appointement.dto.DoctorDto;
import com.appointement.dto.PatientDto;
import com.appointement.entity.Appointment;
import com.appointement.feignclient.DoctorFeignClient;
import com.appointement.feignclient.PatientFeignClient;
import com.appointement.repo.AppointmentRepository;
import com.appointement.request.AppointmentRequest;
import com.appointement.response.AppointmentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
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
@Primary
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository repository;
    private final ModelMapper mapper;
    private final DoctorFeignClient doctorFeignClient;
    private final PatientFeignClient patientFeignClient;
    private final IdGenerator idGenerator;

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
        log.info("Attempting to book appointment for patientId: {}, date: {}",
                request.getPatientId(), request.getAppointmentDate());

        validateTimeSlot(request.getAppointmentDate());

        PatientDto patient = patientFeignClient.getPatientById(request.getPatientId());
        if (patient == null) {
            log.warn("Patient not found with ID: {}", request.getPatientId());
            throw new RuntimeException("Patient not found");
        }
        log.debug("Patient found: {} ({})", patient.getFullName(), patient.getPatientId());

        String assignedDoctorId;
        DoctorDto doctor;

        if (request.getDoctorId() != null && !request.getDoctorId().isEmpty()) {
            log.info("Specific doctor requested: {}", request.getDoctorId());
            doctor = doctorFeignClient.getDoctorById(request.getDoctorId());
            if (doctor == null) {
                log.warn("Requested doctor not found: {}", request.getDoctorId());
                throw new RuntimeException("Doctor not found");
            }
            assignedDoctorId = doctor.getDoctorId();
        } else {
            log.info("No specific doctor requested, finding by specialization: {}", request.getSpecialization());
            List<DoctorDto> doctors = doctorFeignClient.getDoctorsBySpecialization(request.getSpecialization());
            if (doctors.isEmpty()) {
                log.warn("No doctors available for specialization: {}", request.getSpecialization());
                throw new RuntimeException("No doctors found for specialization: " + request.getSpecialization());
            }
            doctor = doctors.get(0); // Taking first available
            assignedDoctorId = doctor.getDoctorId();
            log.info("Assigned first available doctor: {} ({})", doctor.getDoctorName(), assignedDoctorId);
        }

        boolean appointmentExists = repository.existsByPatientIdAndDoctorIdAndAppointmentDate(
                request.getPatientId(), assignedDoctorId, request.getAppointmentDate());

        if (appointmentExists) {
            log.warn("Duplicate appointment detected for patient {} with doctor {} at {}",
                    request.getPatientId(), assignedDoctorId, request.getAppointmentDate());
            throw new RuntimeException("Patient already has an appointment with this doctor at the selected time.");
        }

        Appointment appointment = mapper.map(request, Appointment.class);
        appointment.setAppointmentId(idGenerator.generateAppointmentId());
        appointment.setPatientId(patient.getPatientId());
        appointment.setFullName(patient.getFullName());
        appointment.setDoctorId(assignedDoctorId);
        appointment.setDoctorName(doctor.getDoctorName());
        appointment.setSpecialization(doctor.getSpecialization());

        AppointmentReason reason = request.getReason();
        if (reason == null) {
            log.warn("Appointment reason is missing for patient {}", request.getPatientId());
            throw new IllegalArgumentException("Reason is Required");
        }
        appointment.setReason(reason);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setCreatedAt(LocalDateTime.now());

        Appointment saved = repository.save(appointment);
        log.info("Appointment successfully booked: {} for patient {} with doctor {} on {}",
                saved.getAppointmentId(), saved.getPatientId(), saved.getDoctorId(), saved.getAppointmentDate());

        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentId(saved.getAppointmentId());
        response.setPatientId(saved.getPatientId());
        response.setFullName(saved.getFullName());
        response.setDoctorId(saved.getDoctorId());
        response.setDoctorName(saved.getDoctorName());
        response.setSpecialization(saved.getSpecialization());
        response.setAppointmentDate(saved.getAppointmentDate());
        response.setReason(saved.getReason().toString());
        response.setStatus(saved.getStatus());

        LocalDateTime dateTime = saved.getAppointmentDate();
        response.setDate(dateTime.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH)));
        response.setTime(dateTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
        response.setDay(dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));

        return response;
    }

    public List<AppointmentResponse> getPatientAppointments(String patientId) {
        log.debug("Fetching appointments for patient: {}", patientId);
        return repository.findByPatientId(patientId).stream()
                .map(a -> mapper.map(a, AppointmentResponse.class))
                .toList();
    }

    public List<AppointmentResponse> getDoctorAppointments(String doctorId) {
        log.debug("Fetching appointments for doctor: {}", doctorId);
        return repository.findByDoctorId(doctorId).stream()
                .map(a -> mapper.map(a, AppointmentResponse.class))
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByPatientId(String patientId) {
        log.debug("Fetching appointments by patientId: {}", patientId);
        return repository.findByPatientId(patientId).stream()
                .map(a -> mapper.map(a, AppointmentResponse.class))
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDoctorId(String doctorId) {
        log.debug("Fetching appointments by doctorId: {}", doctorId);
        return repository.findByDoctorId(doctorId).stream()
                .map(a -> mapper.map(a, AppointmentResponse.class))
                .toList();
    }

    private void validateTimeSlot(LocalDateTime appointmentDateTime) {
        if (appointmentDateTime == null) {
            log.warn("Appointment date/time is null");
            throw new IllegalArgumentException("Appointment date and time is required!");
        }

        LocalDateTime now = LocalDateTime.now();
        if (appointmentDateTime.isBefore(now)) {
            log.warn("Attempt to book past appointment: {}", appointmentDateTime);
            throw new IllegalArgumentException("Cannot book appointment in the past!");
        }

        if (appointmentDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            log.info("Appointment requested on Sunday: {}", appointmentDateTime);
            throw new IllegalArgumentException("Clinic is closed on Sundays!");
        }

        LocalTime time = appointmentDateTime.toLocalTime();
        if (!ALLOWED_TIMES.contains(time)) {
            log.warn("Invalid time slot selected: {}", time);
            throw new IllegalArgumentException(
                    "Please select a valid time slot: 10:00 AM, 10:30 AM, 11:00 AM, ..., up to 6:00 PM (30-minute intervals only)"
            );
        }

        log.debug("Time slot validated successfully: {}", time);
    }
}