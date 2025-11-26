package com.doctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication/*
@EntityScan(basePackages = "com.doctor.entity")
@EnableJpaRepositories(basePackages = "com.doctor.repo")*/
public class DoctorServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoctorServiceApplication.class, args);
    }

}
