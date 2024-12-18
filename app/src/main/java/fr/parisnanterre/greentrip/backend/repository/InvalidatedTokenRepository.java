package fr.parisnanterre.greentrip.backend.repository;

import fr.parisnanterre.greentrip.backend.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, Long> {
    boolean existsByToken(String token);
}

