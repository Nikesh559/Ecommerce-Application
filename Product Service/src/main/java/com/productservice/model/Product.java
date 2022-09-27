package com.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String productId;

    @NotNull
    @Column(name = "product")
    private String product;

    @NotNull
    @Column(name = "about_item")
    private String aboutItem;

    @Enumerated(EnumType.STRING)
    @Column(name="category")
    private CategoryConstant category;

    @Column(name="price")
    @Range(min = 1, max = 100000)
    private double price;

    @Column(name="manufacturer")
    private String manufacturer;

    @Column(name = "in_stock")
    private boolean inStock;

    @Column(name="ratings")
    private int ratings;

    @Column(name="warranty")
    private String warranty;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAboutItem() {
        return aboutItem;
    }

    public void setAboutItem(String aboutItem) {
        this.aboutItem = aboutItem;
    }

    public CategoryConstant getCategory() {
        return category;
    }

    public void setCategory(CategoryConstant category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", product='" + product + '\'' +
                ", aboutItem='" + aboutItem + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", inStock=" + inStock +
                ", ratings=" + ratings +
                ", warranty='" + warranty + '\'' +
                '}';
    }
}
