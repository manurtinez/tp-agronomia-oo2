package com.oo2.agronomia.models;

import javax.persistence.Entity;

@Entity
public class Client extends User {
    private String address;

    public Client() {
    }

    public Client(String name, String email, String password, String address) {
        super(name, email, password);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
