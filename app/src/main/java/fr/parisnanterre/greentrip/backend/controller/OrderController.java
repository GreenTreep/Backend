package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.entity.Order;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void createOrder(@AuthenticationPrincipal User user) {
        orderService.createOrderFromCart(user);
    }

    @GetMapping
    public List<Order> getOrders(@AuthenticationPrincipal User user) {
        return orderService.getOrders(user);
    }
}