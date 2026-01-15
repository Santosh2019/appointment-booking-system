/*
// Replace your SecurityConfig with this (Reactive version):
package com.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity  // NOTE: This is different from @EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login", "/register/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .pathMatchers("/dashboard", "/appointments/**").authenticated()
                        .anyExchange().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}*/
