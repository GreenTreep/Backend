package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.controller.CartController;
import fr.parisnanterre.greentrip.backend.entity.CartItem;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCart() {
        User user = new User();
        CartItem item = new CartItem();
        when(cartService.getCart(user)).thenReturn(Collections.singletonList(item));

        List<CartItem> cart = cartController.getCart(user);
        assertEquals(1, cart.size());
    }

    @Test
    public void testAddToCart() {
        User user = new User();
        cartController.addToCart(user, 1L);
        verify(cartService).addToCart(user, 1L);
    }

    @Test
    public void testRemoveFromCart() {
        User user = new User();
        cartController.removeFromCart(user, 1L);
        verify(cartService).removeFromCart(user, 1L);
    }

    @Test
    public void testClearCart() {
        User user = new User();
        cartController.clearCart(user);
        verify(cartService).clearCart(user);
    }
}
