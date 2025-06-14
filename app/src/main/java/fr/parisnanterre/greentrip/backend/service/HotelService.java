package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.Hotel;
import fr.parisnanterre.greentrip.backend.entity.HotelReview;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.HotelRepository;
import fr.parisnanterre.greentrip.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Hotel> getUserFavorites(Long userId) {
        return hotelRepository.findFavoritesByUserId(userId);
    }

    public void addToFavorites(Long userId, Long hotelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        if (user.getFavoriteHotels() == null) {
            user.setFavoriteHotels(new ArrayList<>());
        }
        
        user.getFavoriteHotels().add(hotel);
        userRepository.save(user);
    }

    public void removeFromFavorites(Long userId, Long hotelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        if (user.getFavoriteHotels() != null) {
            user.getFavoriteHotels().remove(hotel);
            userRepository.save(user);
        }
    }

    public HotelReview addReview(Long hotelId, HotelReview review) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        if (hotel.getReviews() == null) {
            hotel.setReviews(new ArrayList<>());
        }

        review.setHotel(hotel);
        hotel.getReviews().add(review);
        hotelRepository.save(hotel);
        
        return review;
    }

    public List<HotelReview> getHotelReviews(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        
        return hotel.getReviews() != null ? hotel.getReviews() : new ArrayList<>();
    }
} 