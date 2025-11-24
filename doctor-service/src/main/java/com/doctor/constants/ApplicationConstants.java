package com.doctor.constants;

public final class ApplicationConstants {

    private ApplicationConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String DOCTOR_SAVED = "Doctor record saved successfully";
    public static final String DOCTOR_UPDATED = "Doctor record updated successfully";
    public static final String DOCTOR_DELETED = "Doctor record deleted successfully";
    public static final String DOCTOR_NOT_FOUND = "Doctor not found with Aadhar: %s";
    public static final String SERVER_ERROR = "Something went wrong. Please try again later.";
}