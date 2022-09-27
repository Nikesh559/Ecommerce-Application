package com.shoppingcart.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class CartItem {
    @EmbeddedId
    private CartItemPK cartItemPK;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(CartItemPK cartItemPK, int quantity, double price) {
        this.cartItemPK = cartItemPK;
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cust_id")
    @MapsId("custId")
    private Customer customer;


    public CartItemPK getCartItemPK() {
        return cartItemPK;
    }

    public void setCartItemPK(CartItemPK cartItemPK) {
        this.cartItemPK = cartItemPK;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
