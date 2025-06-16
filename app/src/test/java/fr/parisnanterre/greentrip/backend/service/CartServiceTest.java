package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.CartItem;
import fr.parisnanterre.greentrip.backend.entity.Product;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.CartItemRepository;
import fr.parisnanterre.greentrip.backend.repository.ProductRepository;
import fr.parisnanterre.greentrip.backend.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CartServiceTest {

    @Mock
    private CartItemRepository cartRepo;

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Product product;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = User.builder().id(1L).email("test@example.com").build();
        product = Product.builder().id(10L).name("Produit A").stock(5).build();
    }

    @Test
    public void testGetCart() {
        CartItem item = CartItem.builder().user(user).product(product).quantity(2).build();
        when(cartRepo.findByUser(user)).thenReturn(Arrays.asList(item));

        List<CartItem> result = cartService.getCart(user);

        assertEquals(1, result.size());
        assertEquals(item, result.get(0));
    }

    @Test
    public void testAddToCart_WhenItemAlreadyExistsAndStockAvailable() {
        CartItem existingItem = CartItem.builder().user(user).product(product).quantity(2).build();

        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepo.findByUserAndProduct(user, product)).thenReturn(Optional.of(existingItem));

        cartService.addToCart(user, product.getId());

        verify(cartRepo).save(existingItem);
        assertEquals(3, existingItem.getQuantity());
    }

    @Test
    public void testAddToCart_WhenItemDoesNotExist() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepo.findByUserAndProduct(user, product)).thenReturn(Optional.empty());

        cartService.addToCart(user, product.getId());

        verify(cartRepo).save(argThat(item
                -> item.getUser().equals(user)
                && item.getProduct().equals(product)
                && item.getQuantity() == 1
        ));
    }

    @Test(expected = RuntimeException.class)
    public void testAddToCart_ProductNotFound() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.empty());
        cartService.addToCart(user, product.getId());
    }

    @Test
    public void testRemoveFromCart() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));

        cartService.removeFromCart(user, product.getId());

        verify(cartRepo).deleteByUserAndProduct(user, product);
    }

    @Test
    public void testClearCart() {
        cartService.clearCart(user);
        verify(cartRepo).deleteAllByUser(user);
    }
}
