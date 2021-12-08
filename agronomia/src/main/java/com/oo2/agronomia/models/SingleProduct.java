package com.oo2.agronomia.models;

import javax.persistence.Entity;

@Entity
public class SingleProduct extends Product {
    private String type;

    public SingleProduct(String name, String type) {
        super(name);
        this.type = type;
    }

    public SingleProduct() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
