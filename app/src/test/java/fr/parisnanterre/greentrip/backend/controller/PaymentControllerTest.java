package fr.parisnanterre.greentrip.backend.controller;

import com.stripe.model.PaymentIntent;
import fr.parisnanterre.greentrip.backend.controller.PaymentController;
import fr.parisnanterre.greentrip.backend.dto.PaymentRequest;
import fr.parisnanterre.greentrip.backend.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private UserDetails userDetails;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPay() throws Exception {
        PaymentRequest req = new PaymentRequest();
        req.setAmount(42.0);
        PaymentIntent mockIntent = mock(PaymentIntent.class);
        when(mockIntent.getClientSecret()).thenReturn("test_secret");

        when(paymentService.handlePayment(req, "test@example.com")).thenReturn(mockIntent);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        ResponseEntity<Map<String, String>> response = paymentController.pay(req, userDetails);

        assertEquals("test_secret", response.getBody().get("clientSecret"));
    }
}
