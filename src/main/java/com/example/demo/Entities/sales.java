package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "sales")
public class sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("client_id")
    @ManyToOne()
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private clients client;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("creation_date")
    private Date creation_date;

    @JsonProperty("sales_details")
    @Transient
    ArrayList<sales_details> detailsList;

    @JsonProperty("total")
    private double total;

    public sales() {
    }

    public sales(int id, clients client, Date creation_date, ArrayList<sales_details> detailsList, double total) {
        this.id = id;
        this.client = client;
        this.creation_date = creation_date;
        this.detailsList = detailsList;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public clients getClient() {
        return client;
    }

    public void setClient(clients client) {
        this.client = client;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<sales_details> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(ArrayList<sales_details> detailsList) {
        this.detailsList = detailsList;
    }
}
