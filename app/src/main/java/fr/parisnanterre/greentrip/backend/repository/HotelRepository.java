package fr.parisnanterre.greentrip.backend.repository;

import fr.parisnanterre.greentrip.backend.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    
    Optional<Hotel> findByExternalIdAndExternalSource(String externalId, String externalSource);
    
    @Query("SELECT h FROM Hotel h JOIN h.favoritedBy u WHERE u.id = :userId")
    List<Hotel> findFavoritesByUserId(@Param("userId") Long userId);
} 