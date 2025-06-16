package fr.parisnanterre.greentrip.backend.service;

import com.stripe.model.PaymentIntent;
import fr.parisnanterre.greentrip.backend.dto.PaymentRequest;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.PaymentHistoryRepository;
import fr.parisnanterre.greentrip.backend.repository.ProductRepository;
import fr.parisnanterre.greentrip.backend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PaymentServiceTest {

    private StripeService stripeService;
    private ProductRepository productRepo;
    private PaymentHistoryRepository historyRepo;
    private UserRepository userRepo;

    private PaymentService paymentService;

    @Before
    public void setUp() {
        stripeService = mock(StripeService.class);
        productRepo = mock(ProductRepository.class);
        historyRepo = mock(PaymentHistoryRepository.class);
        userRepo = mock(UserRepository.class);

        paymentService = new PaymentService(stripeService, productRepo, historyRepo, userRepo);
    }

    @Test
    public void testHandlePayment_success() throws Exception {
        // Arrange
        String email = "user@example.com";
        double amount = 49.99;

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail(email);

        PaymentIntent mockIntent = new PaymentIntent();
        mockIntent.setId("pi_123456");

        when(userRepo.findByEmail(email)).thenReturn(mockUser);
        when(stripeService.createPaymentIntent(amount)).thenReturn(mockIntent);

        PaymentRequest req = new PaymentRequest();
        req.setAmount(amount);

        // Act
        PaymentIntent result = paymentService.handlePayment(req, email);

        // Assert
        assertEquals("pi_123456", result.getId());
        verify(userRepo).findByEmail(email);
        verify(stripeService).createPaymentIntent(amount);

        // Capture the PaymentHistory saved
        ArgumentCaptor<fr.parisnanterre.greentrip.backend.entity.PaymentHistory> captor
                = ArgumentCaptor.forClass(fr.parisnanterre.greentrip.backend.entity.PaymentHistory.class);
        verify(historyRepo).save(captor.capture());

        fr.parisnanterre.greentrip.backend.entity.PaymentHistory savedHistory = captor.getValue();
        assertEquals(mockUser, savedHistory.getUser());
        assertEquals("CREATED", savedHistory.getStatus());
        assertEquals("pi_123456", savedHistory.getPaymentIntentId());
        assertEquals(amount, savedHistory.getAmount(), 0.001);
        assertNotNull(savedHistory.getCreatedAt());
    }

    @Test(expected = RuntimeException.class)
    public void testHandlePayment_userNotFound() throws Exception {
        // Arrange
        String email = "notfound@example.com";
        PaymentRequest req = new PaymentRequest();
        req.setAmount(49.99);

        when(userRepo.findByEmail(email)).thenReturn(null); // Simule utilisateur non trouvé

        // Act
        paymentService.handlePayment(req, email);

        // Assert
        // Exception attendue → aucune autre vérification nécessaire ici
    }

}
