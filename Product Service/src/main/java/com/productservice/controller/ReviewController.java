package com.productservice.controller;

import com.productservice.model.Product;
import com.productservice.model.Review;
import com.productservice.repository.ProductRepository;
import com.productservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewRepository repository;

    @GetMapping("/product/{productId}/reviews")
    public List<Review> getProductReview(@PathVariable("productId") String productId) {
        return repository.findByProduct(productId);
    }

    @PostMapping("/product/{productId}/review")
    public ResponseEntity addReviewToProduct(@PathVariable("productId") String productId, @RequestBody Review review) {
        Product p = new Product();
        p.setProductId(productId);
        review.setProduct(p);
        repository.save(review);
        return new ResponseEntity("Added Review to Product",HttpStatus.CREATED);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity deleteReviewToProduct(@PathVariable("reviewId") String reviewId) {
        repository.deleteById(reviewId);
        return new ResponseEntity("Deleted Review from Product",HttpStatus.OK);
    }
}
