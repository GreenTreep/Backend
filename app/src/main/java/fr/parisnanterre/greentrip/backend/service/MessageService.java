package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.Message;
import fr.parisnanterre.greentrip.backend.entity.Role;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.MessageRepository;
import fr.parisnanterre.greentrip.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Message> saveMessage(Message message) {
        User sender = userRepository.findById(message.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        if (sender.getRole() == Role.USER) {
            // Récupérer tous les administrateurs
            List<User> admins = userRepository.findAllByRole(Role.ADMIN);
            if (admins.isEmpty()) {
                throw new RuntimeException("No admins available to receive the message");
            }

            // Créer un message pour tous les admins
            message.setReceivers(admins);
            message.setTimestamp(LocalDateTime.now());
            return List.of(messageRepository.save(message)); // Sauvegarder un seul message pour tous les admins
        }

        if (sender.getRole() == Role.ADMIN) {
            // Récupérer le premier utilisateur (receiver principal)
            User receiver = userRepository.findById(message.getReceivers().get(0).getId())
                    .orElseThrow(() -> new RuntimeException("Receiver not found"));

            if (receiver.getRole() != Role.USER) {
                throw new RuntimeException("Admins can only send messages to users");
            }

            // Simuler une réponse consolidée (comme si tous les admins avaient répondu)
            message.setReceivers(List.of(receiver));
            message.setTimestamp(LocalDateTime.now());
            return List.of(messageRepository.save(message));
        }

        throw new RuntimeException("Unsupported message flow");
    }


    public List<Message> getConversation(Long user1Id, Long user2Id) {
        return messageRepository.findConversation(user1Id, user2Id);
    }

    public List<Message> getConversationWithAdmins(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.USER) {
            throw new RuntimeException("Only users have a conversation with admins");
        }

        return messageRepository.findConsolidatedConversationWithAdmins(userId);
    }

    public Map<User, List<Message>> getAllDiscussionsByUser() {
        // Récupérez tous les utilisateurs avec le rôle USER
        List<User> users = userRepository.findAllByRole(Role.USER);

        // Pour chaque utilisateur, récupérez ses messages avec les admins
        return users.stream()
                .collect(Collectors.toMap(
                        user -> user, // Clé : utilisateur
                        user -> messageRepository.findConversationWithAdmins(user.getId()) // Valeur : messages avec ADMIN
                ));
    }

    public Message sendMessageFromAdminToUser(Message message) {
        // Récupérer l'utilisateur sender
        User sender = userRepository.findById(message.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // Vérifier que le sender est bien un ADMIN
        if (sender.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admins can send messages to users");
        }

        // Récupérer l'utilisateur destinataire
        User receiver = userRepository.findById(message.getReceivers().get(0).getId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Vérifier que le destinataire est bien un USER
        if (receiver.getRole() != Role.USER) {
            throw new RuntimeException("Messages can only be sent to users");
        }

        // Créer le message
        message.setSender(sender);
        message.setReceivers(List.of(receiver)); // Un seul destinataire
        message.setTimestamp(LocalDateTime.now());

        // Sauvegarder le message
        return messageRepository.save(message);
    }



    /**
         * Envoie un message d'un utilisateur à tous les administrateurs.
         */
    public Message sendMessageFromUserToAdmins(Message message) {
        // Vérifier que l'expéditeur est bien un utilisateur
        User sender = userRepository.findById(message.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        if (sender.getRole() != Role.USER) {
            throw new RuntimeException("Only users can send messages to admins");
        }

        // Récupérer tous les administrateurs
        List<User> admins = userRepository.findAllByRole(Role.ADMIN);
        if (admins.isEmpty()) {
            throw new RuntimeException("No admins available to receive the message");
        }

        // Associer tous les administrateurs comme destinataires
        message.setSender(sender);
        message.setReceivers(admins);
        message.setTimestamp(LocalDateTime.now());

        // Sauvegarder le message
        return messageRepository.save(message);
    }


}

