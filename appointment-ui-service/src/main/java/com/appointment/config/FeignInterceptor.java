package com.appointment.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
class FeignAuthInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String JWT_COOKIE_NAME = "jwt_token"; // tumcha cookie name

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // Option 1: Agar frontend ne Authorization header pathavla asel (e.g. JS fetch)
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                template.header(AUTHORIZATION_HEADER, authHeader);
                return; // success, no need to check cookie
            }

            // Option 2: Agar token cookie madhe store kela asel (safe & common in Thymeleaf/SPA)
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                        String token = cookie.getValue();
                        if (token != null && !token.isEmpty()) {
                            template.header(AUTHORIZATION_HEADER, BEARER_PREFIX + token);
                        }
                        return;
                    }
                }
            }
        }

        // Optional: Agar token nahi milala tar pan call jaude (unauthenticated endpoints sathi)
        // Nahi tar error throw karu shakat
    }
}