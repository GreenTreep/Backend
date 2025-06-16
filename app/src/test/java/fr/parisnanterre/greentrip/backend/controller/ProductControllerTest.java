package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.controller.ProductController;
import fr.parisnanterre.greentrip.backend.entity.Product;
import fr.parisnanterre.greentrip.backend.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductController productController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        Product p1 = new Product(); p1.setName("Bracelet");
        Product p2 = new Product(); p2.setName("Bague");

        when(productRepo.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> result = productController.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Bracelet", result.get(0).getName());
    }
}
