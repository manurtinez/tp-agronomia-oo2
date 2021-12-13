package com.oo2.agronomia.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bolson extends Product {

    @OneToMany
    private List<SingleProduct> products;

    public Bolson(String name) {
        super(name);
        this.products = new ArrayList<>();
    }

    public Bolson() {
        super();
    }

    public List<SingleProduct> getProducts() {
        return this.products;
    }

    public void addProduct(SingleProduct product) {
        this.products.add(product);
    }

    public double getPrice() {
        return this.products.stream().mapToDouble(prod -> prod.price).sum();
    }
}
