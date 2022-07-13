package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "sales_details")
public class sales_details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonProperty("sale_id")
    @ManyToOne()
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    private sales sale;

    @JsonProperty("seller_id")
    @ManyToOne()
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private sellers seller;

    @JsonProperty("product_id")
    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private products product;

    @JsonProperty("quantity")
    @Column(nullable = false)
    private int quantity;

    @JsonProperty("price")
    @Column(nullable = false)
    private double price;

    public sales_details() {
    }

    public sales_details(int id, sales sale, sellers seller, products product, int quantity, double price) {
        this.id = id;
        this.sale = sale;
        this.seller = seller;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public sales getSale() {
        return sale;
    }

    public void setSale(sales sale) {
        this.sale = sale;
    }

    public products getProduct() {
        return product;
    }

    public void setProduct(products product) {
        this.product = product;
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

    public sellers getSeller() {
        return seller;
    }

    public void setSeller(sellers seller) {
        this.seller = seller;
    }
}
