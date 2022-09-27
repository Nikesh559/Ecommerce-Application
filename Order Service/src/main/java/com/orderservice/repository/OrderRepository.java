package com.orderservice.repository;

import com.orderservice.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, String> {
    List<Orders> findByCustId(String custId);
}
