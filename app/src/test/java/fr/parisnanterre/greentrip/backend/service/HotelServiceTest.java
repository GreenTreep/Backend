package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.Hotel;
import fr.parisnanterre.greentrip.backend.entity.HotelReview;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.HotelRepository;
import fr.parisnanterre.greentrip.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel;
    private User user;
    private HotelReview review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Création d'un hôtel de test
        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setCity("Paris");
        hotel.setCountry("France");
        hotel.setExternalId("123");
        hotel.setExternalSource("booking");

        // Création d'un utilisateur de test
        user = new User();
        user.setId(1L);
        user.setFavoriteHotels(new ArrayList<>());

        // Création d'un avis de test
        review = new HotelReview();
        review.setId(1L);
        review.setComment("Great hotel!");
        review.setEcoRating(8);
    }

    @Test
    void getUserFavorites_ShouldReturnFavorites() {
        // Arrange
        List<Hotel> expectedFavorites = List.of(hotel);
        when(hotelRepository.findFavoritesByUserId(1L)).thenReturn(expectedFavorites);

        // Act
        List<Hotel> result = hotelService.getUserFavorites(1L);

        // Assert
        assertEquals(expectedFavorites, result);
        verify(hotelRepository).findFavoritesByUserId(1L);
    }

    @Test
    void addToFavorites_ShouldAddHotelToFavorites() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        // Act
        hotelService.addToFavorites(1L, 1L);

        // Assert
        assertTrue(user.getFavoriteHotels().contains(hotel));
        verify(userRepository).save(user);
    }

    @Test
    void removeFromFavorites_ShouldRemoveHotelFromFavorites() {
        // Arrange
        user.getFavoriteHotels().add(hotel);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        // Act
        hotelService.removeFromFavorites(1L, 1L);

        // Assert
        assertFalse(user.getFavoriteHotels().contains(hotel));
        verify(userRepository).save(user);
    }

    @Test
    void addReview_ShouldAddReviewToHotel() {
        // Arrange
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // Act
        HotelReview result = hotelService.addReview(1L, review);

        // Assert
        assertNotNull(result);
        verify(hotelRepository).save(hotel);
    }

    @Test
    void getHotelReviews_ShouldReturnReviews() {
        // Arrange
        List<HotelReview> expectedReviews = List.of(review);
        hotel.setReviews(expectedReviews);
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        // Act
        List<HotelReview> result = hotelService.getHotelReviews(1L);

        // Assert
        assertEquals(expectedReviews, result);
    }

    @Test
    void addToFavorites_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> hotelService.addToFavorites(1L, 1L));
    }

    @Test
    void addToFavorites_ShouldThrowException_WhenHotelNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> hotelService.addToFavorites(1L, 1L));
    }
} 