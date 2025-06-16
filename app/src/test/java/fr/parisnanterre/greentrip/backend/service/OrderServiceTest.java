package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.*;
import fr.parisnanterre.greentrip.backend.repository.*;
import fr.parisnanterre.greentrip.backend.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepo;

    @Mock
    private CartItemRepository cartRepo;

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Product product;
    private CartItem cartItem;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");

        product = new Product();
        product.setId(10L);
        product.setName("Produit Test");
        product.setPrice(20.0);

        cartItem = new CartItem();
        cartItem.setId(100L);
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(3);
    }

    @Test
    public void testGetOrders_returnsOrdersForUser() {
        Order order = Order.builder().user(user).product(product).quantity(2).orderDate(LocalDateTime.now()).build();
        when(orderRepo.findByUser(user)).thenReturn(Arrays.asList(order));

        List<Order> result = orderService.getOrders(user);

        verify(orderRepo, times(1)).findByUser(user);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0).getUser());
    }

    @Test
    public void testCreateOrderFromCart_shouldSaveOrdersAndClearCart() {
        List<CartItem> cartItems = Arrays.asList(cartItem);
        when(cartRepo.findByUser(user)).thenReturn(cartItems);

        orderService.createOrderFromCart(user);

        // ✅ cartRepo.findByUser
        verify(cartRepo, times(1)).findByUser(user);

        // ✅ orderRepo.save called once
        verify(orderRepo, times(1)).save(argThat(order ->
                order.getUser().equals(user) &&
                        order.getProduct().equals(product) &&
                        order.getQuantity() == 3
        ));

        // ✅ cartRepo.deleteAll called
        verify(cartRepo, times(1)).deleteAll(cartItems);
    }
}
