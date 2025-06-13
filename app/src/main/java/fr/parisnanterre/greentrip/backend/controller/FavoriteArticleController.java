package fr.parisnanterre.greentrip.backend.controller;
import fr.parisnanterre.greentrip.backend.entity.FavoriteArticle;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.service.FavoriteArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteArticleController {

    @Autowired
    private FavoriteArticleService service;

    @GetMapping
    public List<FavoriteArticle> getFavorites(@AuthenticationPrincipal User user) {
        return service.getFavorites(user);
    }

    @PostMapping
    public ResponseEntity<?> addFavorite(@AuthenticationPrincipal User user, @RequestBody FavoriteArticle article) {
        service.addFavorite(user, article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> removeFavorite(@AuthenticationPrincipal User user, @RequestParam String url) {
        service.removeFavorite(user, url);
        return ResponseEntity.ok().build();
    }
}
