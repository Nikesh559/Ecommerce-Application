package com.productservice.controller;

import com.productservice.model.CategoryConstant;
import com.productservice.model.Product;
import com.productservice.model.Review;
import com.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductRepository repository;

    @PostMapping("/product")
    public ResponseEntity createProduct(@RequestBody Product product) {
        repository.save(product);
        return new ResponseEntity("New Product Created", HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public List<CategoryConstant> getProductCategory() {
        return Arrays.stream(CategoryConstant.values()).collect(Collectors.toList());
    }

    @GetMapping(value = "/product/{productId}")
    public Product getProductById(@PathVariable("productId") String productId) {
        return repository.findById(productId).get();
    }

    @GetMapping(value = "/products")
    public List<Product> getProductByCategory(
            @RequestParam(value = "category", defaultValue = "") String categoryConstant,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset) {

        List<Product> products = repository.findAll();
        System.out.println(categoryConstant+" "+products.get(0).getCategory());
        List<Product> filteredProducts = products.stream().
                filter(product -> categoryConstant.equals("") || categoryConstant.equals(product.getCategory().name()))
                .collect(Collectors.toList());

        return filteredProducts;
    }
}
