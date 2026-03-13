package com.appointment.controller;

import com.appointment.dto.PatientDto;
import com.appointment.dto.User;
import com.appointment.feignclient.PatientFeignClient;
import com.appointment.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientFeignClient patientFeignClient;

    @Value("${jwt.secret:default-secret-key-minimum-32-chars-long-here-12345}")
    private String jwtSecret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private final String AUTH_SERVICE_URL = "http://localhost:8081/api/auth";

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String performLogin(@RequestParam String email,
                               @RequestParam String dob,
                               HttpSession session,
                               HttpServletResponse response) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("username", email);
            body.put("password", dob);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> authResponse =
                    restTemplate.postForEntity(AUTH_SERVICE_URL + "/login", request, Map.class);

            if (authResponse.getStatusCode().is2xxSuccessful()) {

                String token = (String) authResponse.getBody().get("token");

                setJwtCookie(response, token);

                User user = userRepository.findByEmail(email);

                if (user != null) {

                    session.setAttribute("userEmail", user.getEmail());
                    session.setAttribute("userRole", user.getRole());

                    // PATIENT LOGIN
                    if ("PATIENT".equalsIgnoreCase(user.getRole())) {

                        PatientDto patient = patientFeignClient.findByEmail(email);

                        session.setAttribute("loggedInPatientId", patient.getPatientId());
                        session.setAttribute("loggedInPatientName", patient.getFullName());

                        return "redirect:/dashboard";
                    }

                    // DOCTOR LOGIN
                    if ("DOCTOR".equalsIgnoreCase(user.getRole())) {

                        session.setAttribute("loggedInDoctorId", user.getId());
                        session.setAttribute("loggedInDoctorName", user.getEmail());

                        return "redirect:/appointments/doctor";
                    }
                }
            }

        } catch (Exception e) {
            log.error("Login error", e);
        }

        return "redirect:/auth/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {

        Cookie deleteCookie = new Cookie("jwt_token", null);
        deleteCookie.setPath("/");
        deleteCookie.setMaxAge(0);
        response.addCookie(deleteCookie);

        return "redirect:/auth/login?logout";
    }

    private void setJwtCookie(HttpServletResponse response, String token) {

        Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(jwtCookie);
    }

    private String generateJwtToken(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}