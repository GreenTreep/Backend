package fr.parisnanterre.greentrip.backend.controller;

import fr.parisnanterre.greentrip.backend.entity.Product;
import fr.parisnanterre.greentrip.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepo;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
