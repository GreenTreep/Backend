package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.entity.Hotel;
import fr.parisnanterre.greentrip.backend.entity.HotelReview;
import fr.parisnanterre.greentrip.backend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/favorites/{userId}")
    public ResponseEntity<List<Hotel>> getUserFavorites(@PathVariable Long userId) {
        return ResponseEntity.ok(hotelService.getUserFavorites(userId));
    }

    @PostMapping("/favorites/{userId}/{hotelId}")
    public ResponseEntity<Void> addToFavorites(
            @PathVariable Long userId,
            @PathVariable Long hotelId) {
        hotelService.addToFavorites(userId, hotelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/favorites/{userId}/{hotelId}")
    public ResponseEntity<Void> removeFromFavorites(
            @PathVariable Long userId,
            @PathVariable Long hotelId) {
        hotelService.removeFromFavorites(userId, hotelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{hotelId}/reviews")
    public ResponseEntity<HotelReview> addReview(
            @PathVariable Long hotelId,
            @RequestBody HotelReview review) {
        return ResponseEntity.ok(hotelService.addReview(hotelId, review));
    }

    @GetMapping("/{hotelId}/reviews")
    public ResponseEntity<List<HotelReview>> getHotelReviews(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getHotelReviews(hotelId));
    }
} 