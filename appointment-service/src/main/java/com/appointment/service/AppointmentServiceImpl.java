package com.appointment.service;

import com.appointment.dto.AppointmentDto;
import com.appointment.entity.Appointment;
import com.appointment.feignclient.DoctorClient;
import com.appointment.feignclient.PatientClient;
import com.appointment.repo.AppointmentRepository;
import com.appointment.request.BookAppointmentRequest;
import com.doctor.dto.DoctorDto;
import com.patient.dto.PatientDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class AppointmentServiceImpl implements AppointmentServices {
    private final AppointmentRepository appointmentRepo;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;
    private final ModelMapper modelMapper;

    @Override
    public AppointmentDto bookAppointment(BookAppointmentRequest request) {
        // Feign calls
        PatientDto patient = patientClient.getPatientByAadhar(request.getPatientAadhar());
        DoctorDto doctor = doctorClient.getDoctorById(request.getDocId());
        Appointment appointment = new Appointment();
        Appointment saved = appointmentRepo.save(appointment);
        return modelMapper.map(saved, AppointmentDto.class);
    }

    @Override
    public List<AppointmentDto> getAppointByPatient(String aadharCard) {
        return null;
    }

    @Override
    public List<AppointmentDto> getAppointmentsByDoctor(String doctorAadhar) {
        return null;
    }

    @Override
    public List<AppointmentDto> getDoctorAppointmentsToday(String doctorAadhar) {
        return null;
    }

    @Override
    public AppointmentDto updateAppointmentStatus(String appointmentId, String status) {
        return null;
    }

    @Override
    public void cancelAppointment(String appointmentId, String cancelledBy) {

    }

    @Override
    public List<LocalTime> getBookedSlotsForDoctor(String doctorId, LocalDate date) {
        return appointmentRepo.findByDoctorIdAndAppointmentDate(doctorId, date)
                .stream()
                .map(Appointment::getAppointmentTime)
                .toList();
    }
}