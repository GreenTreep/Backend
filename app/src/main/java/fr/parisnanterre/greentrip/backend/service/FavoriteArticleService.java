package fr.parisnanterre.greentrip.backend.service;
import fr.parisnanterre.greentrip.backend.entity.FavoriteArticle;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.FavoriteArticleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class FavoriteArticleService {

    @Autowired
    private FavoriteArticleRepository repository;

    public List<FavoriteArticle> getFavorites(User user) {
        return repository.findByUser(user);
    }

    public void addFavorite(User user, FavoriteArticle article) {
        if (!repository.existsByUserAndUrl(user, article.getUrl())) {
            article.setUser(user);
            repository.save(article);
        }
    }

    @Transactional
    public void removeFavorite(User user, String url) {
        repository.deleteByUserAndUrl(user, url);
    }
}
