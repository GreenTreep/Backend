package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.entity.Hotel;
import fr.parisnanterre.greentrip.backend.entity.HotelReview;
import fr.parisnanterre.greentrip.backend.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelControllerTest {

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelController hotelController;

    private Hotel hotel;
    private HotelReview review;
    private List<Hotel> hotels;
    private List<HotelReview> reviews;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Création d'un hôtel de test
        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setCity("Paris");
        hotel.setCountry("France");

        // Création d'un avis de test
        review = new HotelReview();
        review.setId(1L);
        review.setComment("Great hotel!");
        review.setEcoRating(8);

        // Création des listes de test
        hotels = new ArrayList<>();
        hotels.add(hotel);

        reviews = new ArrayList<>();
        reviews.add(review);
    }

    @Test
    void getUserFavorites_ShouldReturnFavorites() {
        // Arrange
        when(hotelService.getUserFavorites(1L)).thenReturn(hotels);

        // Act
        ResponseEntity<List<Hotel>> response = hotelController.getUserFavorites(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(hotels, response.getBody());
        verify(hotelService).getUserFavorites(1L);
    }

    @Test
    void addToFavorites_ShouldAddHotelToFavorites() {
        // Act
        ResponseEntity<Void> response = hotelController.addToFavorites(1L, 1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(hotelService).addToFavorites(1L, 1L);
    }

    @Test
    void removeFromFavorites_ShouldRemoveHotelFromFavorites() {
        // Act
        ResponseEntity<Void> response = hotelController.removeFromFavorites(1L, 1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(hotelService).removeFromFavorites(1L, 1L);
    }

    @Test
    void addReview_ShouldAddReviewToHotel() {
        // Arrange
        when(hotelService.addReview(1L, review)).thenReturn(review);

        // Act
        ResponseEntity<HotelReview> response = hotelController.addReview(1L, review);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(review, response.getBody());
        verify(hotelService).addReview(1L, review);
    }

    @Test
    void getHotelReviews_ShouldReturnReviews() {
        // Arrange
        when(hotelService.getHotelReviews(1L)).thenReturn(reviews);

        // Act
        ResponseEntity<List<HotelReview>> response = hotelController.getHotelReviews(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reviews, response.getBody());
        verify(hotelService).getHotelReviews(1L);
    }
} 