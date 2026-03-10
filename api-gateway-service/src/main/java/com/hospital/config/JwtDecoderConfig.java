package com.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
public class JwtDecoderConfig {

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        String jwkSetUri = "http://localhost:8080/.well-known/jwks.json";
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}