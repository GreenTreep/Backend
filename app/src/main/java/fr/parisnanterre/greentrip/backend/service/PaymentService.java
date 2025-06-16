package fr.parisnanterre.greentrip.backend.service;

import com.stripe.model.PaymentIntent;
import fr.parisnanterre.greentrip.backend.dto.PaymentRequest;
import fr.parisnanterre.greentrip.backend.entity.PaymentHistory;
import fr.parisnanterre.greentrip.backend.entity.Product;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.PaymentHistoryRepository;
import fr.parisnanterre.greentrip.backend.repository.ProductRepository;
import fr.parisnanterre.greentrip.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PaymentService {

    private final StripeService stripeService;
    private final ProductRepository productRepo;
    private final PaymentHistoryRepository historyRepo;
    private final UserRepository userRepo;

    public PaymentIntent handlePayment(PaymentRequest req, String userEmail) throws Exception {

        User user = userRepo.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        PaymentIntent intent = stripeService.createPaymentIntent(req.getAmount());

        // Historiser
        PaymentHistory history = PaymentHistory.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .amount(req.getAmount())
                .status("CREATED")
                .paymentIntentId(intent.getId())
                .build();

        historyRepo.save(history);

        return intent;
    }
}
