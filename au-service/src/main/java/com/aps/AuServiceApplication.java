package com.aps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication(scanBasePackages = "com.aps")
@EnableMethodSecurity
@EnableDiscoveryClient
public class AuServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuServiceApplication.class, args);
    }

}
