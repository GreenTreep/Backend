package fr.parisnanterre.greentrip.backend.config;

import fr.parisnanterre.greentrip.backend.service.LogoutService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final LogoutService logoutService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, LogoutService logoutService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.logoutService = logoutService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Authorization header or invalid format."); // Debug log
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        if (logoutService.isTokenInvalidated(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        System.out.println("Extracted JWT: " + jwt); // Debug log
        userEmail = jwtService.extractUserName(jwt);
        System.out.println("Extracted email from JWT: " + userEmail); // Debug log

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authentication successful for user: " + userEmail); // Debug log
            } else {
                System.out.println("JWT is invalid for user: " + userEmail); // Debug log
            }
        }
        filterChain.doFilter(request, response);
    }

}
