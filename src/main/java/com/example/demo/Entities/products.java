package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    @Nullable
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("creation_date")
    @Nullable
    private Date creation_date;

    @JsonProperty("price")
    private double price;

    @JsonProperty("seller_id")
    @ManyToOne()
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private sellers seller;

    public products() {

    }

    public products(int id, String name, String description, String category, Date creation_date, double price, sellers seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.creation_date = creation_date;
        this.price = price;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
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
