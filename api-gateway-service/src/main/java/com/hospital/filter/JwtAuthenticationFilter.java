package com.hospital.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String username = claims.getSubject();
                String role = claims.get("role", String.class);

                // Role-based access check
                String requiredRole = config.getRequiredRole();
                if (requiredRole != null && !requiredRole.isEmpty()) {
                    if (role == null || !role.equals(requiredRole)) {
                        log.warn("Access denied - Path: {}, Required: {}, User role: {}, Username: {}",
                                exchange.getRequest().getPath(), requiredRole, role, username);
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        exchange.getResponse().getHeaders().add("X-Error", "Insufficient role: required " + requiredRole);
                        return exchange.getResponse().setComplete();
                    }
                }

                // Add headers for downstream
                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("X-Auth-Username", username)
                        .header("X-Auth-Role", role)
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());

            } catch (ExpiredJwtException e) {
                log.warn("JWT Token expired - Path: {}, Token prefix: {}, IP: {}",
                        exchange.getRequest().getPath(), token.substring(0, Math.min(10, token.length())) + "...",
                        exchange.getRequest().getRemoteAddress());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                exchange.getResponse().getHeaders().add("X-Token-Status", "expired");
                exchange.getResponse().getHeaders().add("X-Error", "Token expired");
                return exchange.getResponse().setComplete();
            } catch (JwtException e) {
                log.error("Invalid JWT token - Path: {}, Reason: {}, Token prefix: {}, IP: {}",
                        exchange.getRequest().getPath(), e.getMessage(),
                        token.substring(0, Math.min(10, token.length())) + "...",
                        exchange.getRequest().getRemoteAddress());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                exchange.getResponse().getHeaders().add("X-Error", "Invalid token");
                return exchange.getResponse().setComplete();
            } catch (Exception e) {
                log.error("Unexpected error in JWT filter - Path: {}, Message: {}",
                        exchange.getRequest().getPath(), e.getMessage(), e);
                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                return exchange.getResponse().setComplete();
            }
        };
    }

    public static class Config {
        private String requiredRole;

        public String getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(String requiredRole) {
            this.requiredRole = requiredRole;
        }
    }
}