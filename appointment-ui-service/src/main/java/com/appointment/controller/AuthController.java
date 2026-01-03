package com.appointment.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;  // ya WebClient

    private final String AUTH_SERVICE_URL = "http://localhost:8082/api/auth/login";

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // login.html template render karel
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpServletResponse response) {

        // Auth-service la credentials pathva
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> authResponse = restTemplate.postForEntity(AUTH_SERVICE_URL, request, Map.class);

            String token = (String) authResponse.getBody().get("token");

            // Token HttpOnly cookie madhe set kara (secure ani safe)
            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            // cookie.setSecure(true); // HTTPS var asel tar uncomment kara
            response.addCookie(cookie);

            // Login successful tar dashboard var redirect
            return ResponseEntity.ok().body(Map.of("redirect", "/dashboard"));

        } catch (HttpClientErrorException e) {
            // Wrong credentials
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }


    }
}