package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.*;
import fr.parisnanterre.greentrip.backend.repository.OrderRepository;
import fr.parisnanterre.greentrip.backend.repository.ProductRepository;
import fr.parisnanterre.greentrip.backend.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    @Transactional
    public void createOrderFromCart(User user) {
        List<CartItem> cartItems = cartRepo.findByUser(user);

        for (CartItem item : cartItems) {
            Order order = Order.builder()
                    .user(user)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .orderDate(LocalDateTime.now())
                    .build();
            orderRepo.save(order);
        }

        cartRepo.deleteAll(cartItems); // vider le panier après commande
    }

    public List<Order> getOrders(User user) {
        return orderRepo.findByUser(user);
    }
}
