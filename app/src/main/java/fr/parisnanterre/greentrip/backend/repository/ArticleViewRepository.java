package fr.parisnanterre.greentrip.backend.repository;

import fr.parisnanterre.greentrip.backend.entity.ArticleView;
import org.springframework.data.jpa.repository.JpaRepository;
import fr.parisnanterre.greentrip.backend.entity.User;
import java.time.LocalDateTime;
import java.util.List;

public interface ArticleViewRepository extends JpaRepository<ArticleView, Long> {
    List<ArticleView> findByUser(User user);
    List<ArticleView> findByUserOrderByViewedAtDesc(User user);
    List<ArticleView> findByUserAndViewedAtAfter(User user, LocalDateTime after);

}
