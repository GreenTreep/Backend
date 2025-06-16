package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.controller.OrderController;
import fr.parisnanterre.greentrip.backend.entity.Order;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrders() {
        User user = new User();
        Order order = new Order();
        when(orderService.getOrders(user)).thenReturn(Collections.singletonList(order));

        List<Order> orders = orderController.getOrders(user);
        assertEquals(1, orders.size());
    }

    @Test
    public void testCreateOrder() {
        User user = new User();
        orderController.createOrder(user);
        verify(orderService).createOrderFromCart(user);
    }
}
