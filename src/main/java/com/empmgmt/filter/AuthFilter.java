package com.empmgmt.filter;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.empmgmt.service.JWTService;
import com.empmgmt.service.UserService;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Lazy
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println("Incoming request: " + requestURI);

        if (requestURI.startsWith("/api/auth/register") || requestURI.startsWith("/api/auth/login")) {
            System.out.println("Allowing request without authentication: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Missing or invalid JWT, proceeding without authentication.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            String email = jwtService.extractEmail(token);
            String userIdStr = jwtService.extractUserId(token);
            String role = jwtService.extractRole(token);

            if (email == null || userIdStr == null || role == null) {
                System.out.println("Token extraction failed!");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token processing");
                return;
            }

            UUID userId;
            try {
                userId = UUID.fromString(userIdStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format in token.");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token format");
                return;
            }

            System.out.println("Extracted Role from Token: " + role);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(email);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("Authentication successful for: " + email);
                } else {
                    System.out.println("Token validation failed for: " + email);
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
                    return;
                }
            }

            request.setAttribute("userId", userId);
            request.setAttribute("role", role);

        } catch (Exception e) {
            System.out.println("Error while processing JWT: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token validation error");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
