package fr.parisnanterre.greentrip.backend.controller;

import com.stripe.model.PaymentIntent;
import fr.parisnanterre.greentrip.backend.dto.PaymentRequest;
import fr.parisnanterre.greentrip.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Map<String, String>> pay(
            @RequestBody PaymentRequest req,
            @AuthenticationPrincipal UserDetails user
    ) throws Exception {
        PaymentIntent intent = paymentService.handlePayment(req, user.getUsername());

        return ResponseEntity.ok(Map.of("clientSecret", intent.getClientSecret()));
    }
}
