package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.entity.CartItem;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCart(@AuthenticationPrincipal User user) {
        return cartService.getCart(user);
    }

    @PostMapping("/{productId}")
    public void addToCart(@AuthenticationPrincipal User user, @PathVariable Long productId) {
        cartService.addToCart(user, productId);
    }

    @DeleteMapping("/{productId}")
    public void removeFromCart(@AuthenticationPrincipal User user, @PathVariable Long productId) {
        cartService.removeFromCart(user, productId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@AuthenticationPrincipal User user) {
        cartService.clearCart(user);
    }

}
