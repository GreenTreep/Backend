package fr.parisnanterre.greentrip.backend.repository;

import fr.parisnanterre.greentrip.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
