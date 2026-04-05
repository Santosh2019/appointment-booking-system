package com.appointment.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public CustomLoginFailureHandler() {
        super("/auth/login?error=auth_failed");  // default fallback URL
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        System.out.println("Login failed for IP " + request.getRemoteAddr() +
                " → " + exception.getClass().getSimpleName() + ": " + exception.getMessage());

        if (exception.getMessage().contains("Bad credentials")) {
            getRedirectStrategy().sendRedirect(request, response, "/auth/login?error=invalid_credentials");
        } else {
            super.onAuthenticationFailure(request, response, exception);  // uses default URL + forwards exception
        }
    }
}
