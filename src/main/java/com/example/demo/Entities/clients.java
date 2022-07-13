package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "clients",uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "last_name","mobile" }) })
public class clients{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("name")
    @Column(nullable = false)
    private String name;

    @JsonProperty("last_name")
    @Column(nullable = false)
    private String last_name;

    @JsonProperty("mobile")
    @Column(nullable = false)
    private String mobile;

    public clients() {
    }

    public clients(int id, String name, String last_name, String mobile) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.mobile = mobile;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
