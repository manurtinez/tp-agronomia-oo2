package com.oo2.agronomia.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String email;

    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Purchase> purchases = new HashSet<>();

    // Constructor vacio
    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.setPassword(password);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}