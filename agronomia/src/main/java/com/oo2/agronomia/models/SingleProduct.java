package com.oo2.agronomia.models;

import javax.persistence.Entity;

@Entity
public class SingleProduct extends Product {
    private String type;

    public SingleProduct(String name, String type, double price) {
        super(name);
        this.type = type;
        this.price = price;
    }

    public SingleProduct() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public int getAmountProducts() {
        return 1;
    }
}
