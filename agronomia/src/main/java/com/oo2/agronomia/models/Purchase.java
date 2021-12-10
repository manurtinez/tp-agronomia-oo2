package com.oo2.agronomia.models;

import com.oo2.agronomia.models.strategy.PurchaseStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String paymentMethod;

    private double totalAmount;

    // Esta es la estrategia que se va a usar para realizar la compra
    @ManyToOne
    private PurchaseStrategy purchaseStrategy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User client;

    @OneToMany
    private List<Product> products;

    public Purchase() {
    }

    public Purchase(String paymentMethod, User client, List<Product> products, PurchaseStrategy purStrat) {
        this.setPaymentMethod(paymentMethod);
        this.setClient(client);
        this.products = products;
        this.purchaseStrategy = purStrat;
        this.totalAmount = this.purchaseStrategy.calculatePurchasePrice(products);
        client.addPurchase(this);
    }

    /**
     * @return the client
     */
    public User getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(User client) {
        this.client = client;
    }

    /**
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public double getTotalAmount() {
        return this.totalAmount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
