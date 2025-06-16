package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.dto.ArticleViewRequest;
import fr.parisnanterre.greentrip.backend.entity.ArticleView;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.ArticleViewRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import fr.parisnanterre.greentrip.backend.service.NewsService;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api/v1/news")
public class NewsTrackingController {

    @Autowired
    private ArticleViewRepository repository;

    @Autowired
    private NewsService newsService;

    @GetMapping("/external-news")
    public ResponseEntity<?> fetchExternalNews(@RequestParam String topic) {
        try {
            String response = newsService.fetchNewsByTopic(topic);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la récupération des actualités : " + e.getMessage());
        }
    }


    @PostMapping("/view")
    public ResponseEntity<?> trackArticleView(@AuthenticationPrincipal User user,
                                              @RequestBody ArticleViewRequest request) {
        ArticleView view = new ArticleView();
        view.setTitle(request.getTitle());
        view.setUrl(request.getUrl());
        view.setViewedAt(LocalDateTime.now());
        view.setUser(user);
        repository.save(view);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/views")
    public ResponseEntity<?> getUserViews(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(repository.findByUserOrderByViewedAtDesc(user));
    }

    @GetMapping("/views/stats")
    public ResponseEntity<?> getStats(@AuthenticationPrincipal User user) {
        var views = repository.findByUser(user);
        long total = views.size();
        String lastTitle = views.isEmpty() ? null : views.get(views.size() - 1).getTitle();
        return ResponseEntity.ok(new StatsResponse(total, lastTitle));
    }
    public record StatsResponse(long totalViews, String lastViewedTitle) {}

    @GetMapping("/views/last-days")
    public ResponseEntity<?> getLastNDaysViews(@AuthenticationPrincipal User user,
                                               @RequestParam int days) {
        LocalDateTime from = LocalDateTime.now().minusDays(days);
        return ResponseEntity.ok(repository.findByUserAndViewedAtAfter(user, from));
    }

    @GetMapping("/views/top")
    public ResponseEntity<?> getTopArticles() {
        List<ArticleView> allViews = repository.findAll();
        Map<String, Long> grouped = allViews.stream()
                .collect(Collectors.groupingBy(ArticleView::getUrl, Collectors.counting()));

        List<TopArticle> top = grouped.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(e -> new TopArticle(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(top);
    }

    public record TopArticle(String url, long views) {}

    @GetMapping("/views/export")
    public void exportCsv(@AuthenticationPrincipal User user, HttpServletResponse response) throws IOException {
        List<ArticleView> views = repository.findByUserOrderByViewedAtDesc(user);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=views.csv");

        PrintWriter writer = response.getWriter();
        writer.println("Title,URL,ViewedAt");
        for (ArticleView v : views) {
            writer.printf("\"%s\",%s,%s\n",
                    v.getTitle().replaceAll("\"", "\"\""),
                    v.getUrl(),
                    v.getViewedAt());
        }
        writer.flush();
    }
}