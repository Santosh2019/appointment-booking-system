package com.appointment.response;

import java.time.LocalTime;

public record AvailableSlotResponse(
        LocalTime time,
        boolean available,
        String message
) {
}
