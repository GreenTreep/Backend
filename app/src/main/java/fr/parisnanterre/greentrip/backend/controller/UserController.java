package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/first-name")
    public ResponseEntity<String> getCurrentUserFirstName(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user.getFirstName());
    }

    @GetMapping
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User principal) {
        if (principal == null) {
            // Retourne une réponse structurée en cas de non-authentification
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
            errorResponse.put("message", "User not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Prépare les détails utilisateur
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", principal.getId());
        userDetails.put("firstName", principal.getFirstName());
        userDetails.put("lastName", principal.getLastName());
        userDetails.put("email", principal.getEmail());
        userDetails.put("role", principal.getRole());

        return ResponseEntity.ok(userDetails);
    }
}
