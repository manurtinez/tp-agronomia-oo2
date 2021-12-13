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

    // Este seria el precio total de la compra
    private double totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User client;

    @ManyToMany
    private List<Product> productList;

    // Esta es la estrategia que se va a usar para realizar la compra
//    private PurchaseStrategy purchaseStrategy;

    // Constructor que INCLUYE la strategy
    public Purchase(String paymentMethod, User client, List<Product> productList, PurchaseStrategy strat) {
        this.setPaymentMethod(paymentMethod);
        this.setClient(client);
        this.setProductList(productList);
        // Se asigna el total usando la estrategia elegida
        this.setTotalAmount(strat.calculatePurchasePrice(productList));
//        this.setPurchaseStrategy(strat);
        client.addPurchase(this);
    }

    public Purchase() {
    }

    public Purchase(String paymentMethod, User client, List<Product> productList) {
        this.setPaymentMethod(paymentMethod);
        this.setClient(client);
        // Se asigna el total sumando los productos
        this.setTotalAmount(productList.stream().mapToDouble(Product::getPrice).sum());
        this.setProductList(productList);
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

//    public PurchaseStrategy getPurchaseStrategy() {
//        return purchaseStrategy;
//    }
//
//    public void setPurchaseStrategy(PurchaseStrategy purchaseStrategy) {
//        this.purchaseStrategy = purchaseStrategy;
//    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
