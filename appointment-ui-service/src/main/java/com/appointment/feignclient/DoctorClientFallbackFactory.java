package com.appointment.feignclient;


import com.appointment.dto.DoctorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DoctorClientFallbackFactory implements FallbackFactory<DoctorFeignClient> {

    @Override
    public DoctorFeignClient create(Throwable cause) {
        log.warn("DOCTOR-SERVICE DOWN! Fallback activated → Cause: {}", cause.toString());
        return new DoctorFeignClient() {

            @Override
            public DoctorDto getDoctorById(String doctorId) {
                log.info("Fallback → getDoctorById({})", doctorId);
                DoctorDto fallback = new DoctorDto();
                //fallback.setDoctorId(doctorId);
                fallback.setDoctorName("Dr. Emergency service Unavailable");
                fallback.setSpecialization("General");
                return fallback;
            }

            @Override
            public DoctorDto addDoctor(DoctorDto doctorDto) {
                return null;
            }

            @Override
            public List<DoctorDto> getDoctorsBySpecialization(String spec) {
                log.info("Fallback → getDoctorsBySpecialization({})", spec);
                DoctorDto emergency = new DoctorDto();
                emergency.setDoctorId("fallback-999");
                emergency.setDoctorName("Dr. Emergency - " + spec);
                emergency.setSpecialization(spec);
                return List.of(emergency);
            }
        };
    }
}