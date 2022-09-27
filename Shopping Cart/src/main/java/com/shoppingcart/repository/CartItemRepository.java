package com.shoppingcart.repository;

import com.shoppingcart.model.CartItem;
import com.shoppingcart.model.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {
    @Query("SELECT i FROM CartItem i WHERE i.customer.custId = :custId")
    List<CartItem> findByCustId(@Param("custId") String custId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem i WHERE i.customer.custId = :custId")
    void deleteByCustId(@Param("custId") String custId);

}
