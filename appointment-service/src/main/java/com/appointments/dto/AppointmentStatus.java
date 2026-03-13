package com.appointments.dto;

public enum AppointmentStatus {

    SCHEDULED("Appointment Booked"),
    CONFIRMED("Doctor Confirmed"),
    CANCELLED("Appointment Cancelled"),
    COMPLETED("Visit Completed");

    private final String label;

    AppointmentStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}