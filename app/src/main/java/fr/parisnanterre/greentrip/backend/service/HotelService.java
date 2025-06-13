package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.Hotel;
import fr.parisnanterre.greentrip.backend.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public List<Hotel> getHotelsByCity(String city) {
        return hotelRepository.findByCity(city);
    }

    public List<Hotel> getHotelsByCountry(String country) {
        return hotelRepository.findByCountry(country);
    }

    public List<Hotel> getEcoFriendlyHotels() {
        return hotelRepository.findByIsEcoFriendlyTrue();
    }

    public List<Hotel> getHotelsNearby(Double latitude, Double longitude, Double radius) {
        return hotelRepository.findHotelsNearby(latitude, longitude, radius);
    }

    public List<Hotel> getHotelsByMaxPrice(Double maxPrice) {
        return hotelRepository.findByPricePerNightLessThanEqual(maxPrice);
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
} 