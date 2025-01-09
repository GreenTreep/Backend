package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.entity.Message;
import fr.parisnanterre.greentrip.backend.entity.Role;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.service.MessageService;
import fr.parisnanterre.greentrip.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    // Envoi d'un message
    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        try {
            System.out.println("Received message payload: " + message);

            // Vérifiez que le sender existe et qu'il a le rôle ADMIN
            if (message.getSender() == null || message.getSender().getId() == null) {
                System.out.println("Sender is missing or invalid.");
                return ResponseEntity.badRequest().body(null);
            }

            // Appel du service pour envoyer le message
            Message savedMessage = messageService.sendMessageFromAdminToUser(message);
            return ResponseEntity.ok(savedMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    // Récupérer une conversation spécifique entre deux utilisateurs
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam Long user1Id,
            @RequestParam Long user2Id
    ) {
        List<Message> messages = messageService.getConversation(user1Id, user2Id);
        return ResponseEntity.ok(messages);
    }

    // Récupérer la conversation consolidée entre un utilisateur et les admins
    @GetMapping("/user/{userId}/admins")
    public ResponseEntity<List<Message>> getConversationWithAdmins(@PathVariable Long userId) {
        List<Message> messages = messageService.getConversationWithAdmins(userId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/from-user")
    public ResponseEntity<Message> sendMessageFromUser(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.sendMessageFromUserToAdmins(message);
            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
