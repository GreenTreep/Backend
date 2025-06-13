package fr.parisnanterre.greentrip.backend.repository;
import fr.parisnanterre.greentrip.backend.entity.FavoriteArticle;
import fr.parisnanterre.greentrip.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteArticleRepository extends JpaRepository<FavoriteArticle, Long> {
    List<FavoriteArticle> findByUser(User user);
    boolean existsByUserAndUrl(User user, String url);
    void deleteByUserAndUrl(User user, String url);
}
