package com.organization.taskdirectory.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ADMINISTRATOR")) {
            response.sendRedirect("/admin/dashboard");
        } else if (role.equals("SUPERVISOR")) {
            response.sendRedirect("/supervisor/dashboard");
        } else if (role.equals("USER")) {
            response.sendRedirect("/user/dashboard");
        } else {
            response.sendRedirect("/access-denied");
        }
    }
}
