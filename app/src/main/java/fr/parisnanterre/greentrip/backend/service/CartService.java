package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.CartItem;
import fr.parisnanterre.greentrip.backend.entity.Product;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.CartItemRepository;
import fr.parisnanterre.greentrip.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    public List<CartItem> getCart(User user) {
        return cartRepo.findByUser(user);
    }

    public void addToCart(User user, Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        cartRepo.findByUserAndProduct(user, product).ifPresentOrElse(
                item -> {
                    if (item.getQuantity() < product.getStock()) {
                        item.setQuantity(item.getQuantity() + 1);
                        cartRepo.save(item);
                    }
                },
                () -> cartRepo.save(CartItem.builder()
                        .user(user)
                        .product(product)
                        .quantity(1)
                        .build())
        );
    }

    public void removeFromCart(User user, Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        cartRepo.deleteByUserAndProduct(user, product);
    }

    public void clearCart(User user) {
        cartRepo.deleteAllByUser(user);
    }
}

