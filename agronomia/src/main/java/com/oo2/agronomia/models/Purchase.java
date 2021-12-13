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

    @ManyToMany
    private List<Product> productList;

    public Purchase() {
    }

    public Purchase(String paymentMethod, User client, List<Product> productList, PurchaseStrategy purStrat) {
        this.setPaymentMethod(paymentMethod);
        this.setClient(client);
        this.setProductList(productList);
        this.purchaseStrategy = purStrat;
        this.totalAmount = this.purchaseStrategy.calculatePurchasePrice(productList);
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    // Este metodo devuelve el total de productos tanto simples como bolson
    public int getTotalOfProducts() {
        return productList.stream().mapToInt(Product::getAmountProducts).sum();
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }
}
