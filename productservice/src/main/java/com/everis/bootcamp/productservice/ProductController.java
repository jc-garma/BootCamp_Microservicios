package com.everis.bootcamp.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getPrices() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addPrice(@RequestBody final Product product) {
        return (product != null && product.getId()!= null) ? ResponseEntity.ok(productService.insert(product)) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getPrice(@PathVariable final Integer productId) {
        if (productId == 0) {
            throw new IllegalArgumentException();
        }
        return (productId != null) ? ResponseEntity.ok(productService.findOne(productId.toString())) : ResponseEntity.badRequest().build();
    }
}

