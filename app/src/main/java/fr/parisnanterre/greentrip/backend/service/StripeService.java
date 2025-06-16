package fr.parisnanterre.greentrip.backend.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new RuntimeException("❌ Clé Stripe absente dans les variables d’environnement !");
        }
        Stripe.apiKey = secretKey;
        System.out.println("✅ Clé Stripe chargée depuis les variables d’environnement !");
    }

    public PaymentIntent createPaymentIntent(Double amount) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (amount * 100)) // convertir en centimes
                .setCurrency("eur")
                .build();

        return PaymentIntent.create(params);
    }
}