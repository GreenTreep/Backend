package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.entity.Message;
import fr.parisnanterre.greentrip.backend.entity.Role;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.service.MessageService;
import fr.parisnanterre.greentrip.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/support")
public class SupportController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/discussions")
    public ResponseEntity<Map<User, List<Message>>> getDiscussions() {
        // Récupérer l'utilisateur connecté
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return ResponseEntity.status(403).build(); // Non autorisé
        }

        UserDetails userDetails = (UserDetails) principal;
        User admin = userService.findByEmail(userDetails.getUsername());

        // Vérifiez si l'utilisateur est un ADMIN
        if (admin.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).build(); // Accès interdit
        }

        // Récupérez toutes les discussions organisées par utilisateur
        Map<User, List<Message>> discussions = messageService.getAllDiscussionsByUser();
        return ResponseEntity.ok(discussions);
    }
}
