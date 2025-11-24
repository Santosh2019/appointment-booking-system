package com.patient.ApplicationConstants;

public final class ApplicationConstants {

    private ApplicationConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String DOCTOR_SAVED = "Patient record saved successfully";
    public static final String DOCTOR_UPDATED = "Patient record updated successfully";
    public static final String DOCTOR_DELETED = "Patient record deleted successfully";
    public static final String DOCTOR_NOT_FOUND = "Patient not found with Aadhar: %s";
    public static final String SERVER_ERROR = "Something went wrong. Please try again later.";
}