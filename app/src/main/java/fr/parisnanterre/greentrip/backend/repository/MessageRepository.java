package fr.parisnanterre.greentrip.backend.repository;

import fr.parisnanterre.greentrip.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Trouve une conversation entre deux utilisateurs en fonction de leurs IDs.
     */
    @Query("SELECT m FROM Message m JOIN m.receivers r WHERE " +
            "(m.sender.id = :user1Id AND r.id = :user2Id) OR " +
            "(m.sender.id = :user2Id AND r.id = :user1Id) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findConversation(Long user1Id, Long user2Id);

    /**
     * Trouve les messages échangés entre un utilisateur et les administrateurs.
     */
    @Query("SELECT m FROM Message m JOIN m.receivers r WHERE " +
            "(m.sender.role = 'USER' AND m.sender.id = :userId AND r.role = 'ADMIN') OR " +
            "(r.id = :userId AND m.sender.role = 'ADMIN') " +
            "ORDER BY m.timestamp ASC")
    List<Message> findConversationWithAdmins(Long userId);

    /**
     * Regroupe toutes les conversations d'un utilisateur avec les administrateurs.
     */
    @Query("SELECT m FROM Message m JOIN m.receivers r WHERE " +
            "(m.sender.id = :userId AND r.role = 'ADMIN') OR " +
            "(r.id = :userId AND m.sender.role = 'ADMIN') " +
            "ORDER BY m.timestamp ASC")
    List<Message> findConsolidatedConversationWithAdmins(Long userId);
}
