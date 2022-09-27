package com.productservice.repository;

import com.productservice.model.Product;
import com.productservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

}
