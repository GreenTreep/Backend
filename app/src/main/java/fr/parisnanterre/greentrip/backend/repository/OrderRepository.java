package fr.parisnanterre.greentrip.backend.repository;

import fr.parisnanterre.greentrip.backend.entity.Order;
import fr.parisnanterre.greentrip.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
