package com.productservice.repository;

import com.productservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM Review r WHERE r.product_id = ?")
    List<Review> findByProduct(@Param("productId") String productId);

}
