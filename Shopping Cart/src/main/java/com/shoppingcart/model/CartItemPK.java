package com.shoppingcart.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemPK implements Serializable{
    private String productId;
    private String custId;

    public CartItemPK(String productId, String custId) {
        this.productId = productId;
        this.custId = custId;
    }

    public CartItemPK() {

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemPK that = (CartItemPK) o;
        return productId.equals(that.productId) && custId.equals(that.custId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, custId);
    }
}
