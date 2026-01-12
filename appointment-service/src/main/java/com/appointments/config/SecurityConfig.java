package com.appointments.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration                  // <-- He compulsory ahe
@EnableWebSecurity            // Spring Boot 3.1+ madhe he optional ahe (auto-configuration karto)
// pan juna version madhe error yet asel tar he pan add kara
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())                     // CSRF band
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()                     // Gateway ne JWT check kela ahe → sagla allow
                );

        return http.build();
    }
}