package fr.parisnanterre.greentrip.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Référence à l'API externe
    private String externalId;
    private String externalSource; // ex: "booking", "hotels.com", etc.

    // Informations de base
    private String name;
    private String city;
    private String country;

    // Relations
    @ManyToMany(mappedBy = "favoriteHotels")
    private List<User> favoritedBy;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<HotelReview> reviews;
} 