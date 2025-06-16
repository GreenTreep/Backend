package fr.parisnanterre.greentrip.backend.repository;

import fr.parisnanterre.greentrip.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
