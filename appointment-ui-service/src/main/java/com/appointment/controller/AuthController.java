package com.appointment.controller;

import com.appointment.dto.User;
import com.appointment.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

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

    @Value("${jwt.secret:default-secret-key-minimum-32-chars-long-here-12345}")
    private String jwtSecret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        try {
            this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            log.info("JWT signing key initialized successfully | key length = {} bits",
                    key.getEncoded().length * 8);
        } catch (Exception e) {
            log.error("Failed to initialize JWT signing key", e);
            throw new RuntimeException("JWT key initialization failed", e);
        }
    }

    private final String AUTH_SERVICE_URL = "http://localhost:8081/api/auth";

    @GetMapping("/login")
    public String showLoginPage() {
        log.info("Showing login page");
        return "login";
    }

    @PostMapping("/login")
    public String performLogin(@RequestParam("email") String email,
                               @RequestParam("dob") String dob,
                               HttpServletResponse response) {

        log.info("Login attempt | email = {}, dob = {}", email, dob);

        if (!isAuthServiceAvailable()) {
            log.warn("Auth service not reachable → falling back to local authentication");
            return fallbackLogin(email, dob, response);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("username", email);
            body.put("password", dob);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

            log.debug("Sending auth request to microservice → url = {}, body = {}",
                    AUTH_SERVICE_URL + "/login", body);

            ResponseEntity<Map> authResponse = restTemplate.postForEntity(
                    AUTH_SERVICE_URL + "/login",
                    request,
                    Map.class);

            log.info("Auth service response → status = {}, body = {}",
                    authResponse.getStatusCode(), authResponse.getBody());

            if (authResponse.getStatusCode().is2xxSuccessful()) {
                String token = (String) authResponse.getBody().get("token");

                if (token == null || token.trim().isEmpty()) {
                    log.warn("Auth service returned empty/null token for email = {}", email);
                    return "redirect:/auth/login?error=empty_token";
                }

                setJwtCookie(response, token);
                log.info("Login successful | email = {}, token issued (length={})",
                        email, token.length());
                return "redirect:/dashboard";
            } else {
                log.warn("Auth service returned non-success status | code = {}, response = {}",
                        authResponse.getStatusCode(), authResponse.getBody());
                return "redirect:/auth/login?error=auth_failed";
            }

        } catch (HttpClientErrorException e) {
            log.warn("Auth service rejected credentials | status = {}, response = {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            return "redirect:/auth/login?error=invalid_credentials";

        } catch (ResourceAccessException e) {
            log.error("Cannot reach auth service → falling back to local auth", e);
            return fallbackLogin(email, dob, response);

        } catch (Exception e) {
            log.error("Unexpected error during login flow | email = {}", email, e);
            return "redirect:/auth/login?error=server_error";
        }
    }

    private String fallbackLogin(String email, String dobStr, HttpServletResponse response) {
        log.info("Fallback login triggered | email = {}, dob = {}", email, dobStr);

        try {
            User user = userRepository.findByEmail(email);

            if (user == null) {
                log.info("Fallback: User not found | email = {}", email);
                return "redirect:/auth/login?error=invalid_credentials";
            }

            log.debug("Fallback: Found user | id = {}, email = {}, dob = {}, role = {}",
                    user.getId(), user.getEmail(), user.getDob(), user.getRole());
            if (user.getDob().toString().equals(dobStr)) {
                String token = generateJwtToken(user);
                setJwtCookie(response, token);
                log.info("Fallback login SUCCESS | email = {}, userId = {}, role = {}",
                        email, user.getId(), user.getRole());
                return "redirect:/dashboard";
            } else {
                log.info("Fallback: DOB mismatch | email = {}", email);
                return "redirect:/auth/login?error=invalid_credentials";
            }

        } catch (Exception e) {
            log.error("Fallback authentication failed | email = {}", email, e);
            return "redirect:/auth/login?error=service_unavailable";
        }
    }

    @PostMapping("/v1/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> loginApi(@RequestBody Map<String, String> request) {
        log.info("API login attempt (v1) | request = {}", request);

        try {
            String email = request.get("email");
            String dobStr = request.get("dob");

            if (email == null || dobStr == null) {
                log.warn("API login → missing required fields");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Email and DOB are required"));
            }

            LocalDate dob = LocalDate.parse(dobStr);
            User user = userRepository.findByEmail(email);

            if (user == null) {
                log.info("API login → user not found | email = {}", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }

            if (user.getDob().equals(dob)) {
                String token = generateJwtToken(user);
                log.info("API login SUCCESS | email = {}, userId = {}", email, user.getId());
                return ResponseEntity.ok(Map.of("token", token));
            }

            log.info("API login → invalid DOB | email = {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));

        } catch (Exception e) {
            log.error("API v1 login failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Server error"));
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        log.info("Logout requested");

        Cookie deleteCookie = new Cookie("jwt_token", null);
        deleteCookie.setPath("/");
        deleteCookie.setMaxAge(0);
        deleteCookie.setHttpOnly(true);
        response.addCookie(deleteCookie);

        log.info("Logout completed → jwt_token cookie cleared");
        return "redirect:/auth/login?logout";
    }

    // ────────────────────────────────────────────────
    //          Helper methods
    // ────────────────────────────────────────────────

    private boolean isAuthServiceAvailable() {
        try {
            ResponseEntity<String> health = restTemplate.getForEntity(
                    AUTH_SERVICE_URL + "/health", String.class);
            log.debug("Auth service health check → status = {}, body = {}",
                    health.getStatusCode(), health.getBody());
            return true;
        } catch (Exception e) {
            log.debug("Auth service health check failed → {}", e.getMessage());
            return false;
        }
    }

    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);
        jwtCookie.setSecure(false);           // ← change to true in production!
        jwtCookie.setAttribute("SameSite", "Strict");
        response.addCookie(jwtCookie);

        log.debug("JWT cookie set | length = {}, path = /, SameSite=Strict", token.length());
    }

    private String generateJwtToken(User user) {
        long expirationMs = 7 * 24 * 60 * 60 * 1000;

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .claim("dob", user.getDob().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        log.debug("Generated JWT | subject = {}, userId = {}, role = {}, length = {}",
                user.getEmail(), user.getId(), user.getRole(), token.length());

        return token;
    }
}