package fr.parisnanterre.greentrip.backend.controller.auth;

import fr.parisnanterre.greentrip.backend.config.JwtService;
import fr.parisnanterre.greentrip.backend.service.LogoutService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final LogoutService logoutService;

    public AuthenticationController(AuthenticationService service, UserDetailsService userDetailsService, JwtService jwtService, LogoutService logoutService) {
        this.service = service;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.logoutService = logoutService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ){
        // return ResponseEntity.ok(service.authenticate(request));
        try {
            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Refresh token is required");
        }

        try {
            String username = jwtService.extractUserName(refreshToken);
            if (username == null || !jwtService.isTokenValid(refreshToken, userDetailsService.loadUserByUsername(username))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

            // Generate a new access token
            String newAccessToken = jwtService.generateToken(userDetailsService.loadUserByUsername(username));
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (ExpiredJwtException ex) {
            // Handle refresh token expiry separately if needed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token has expired. Please log in again.");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("No token provided");
        }

        String jwt = authHeader.substring(7);
        logoutService.logout(jwt);

        return ResponseEntity.ok("Successfully logged out");
    }
}
