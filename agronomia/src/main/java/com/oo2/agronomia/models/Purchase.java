package com.oo2.agronomia.models;

import javax.persistence.*;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String paymentMethod;

    @ManyToOne
    private User client;

    public Purchase() {
    }

    public Purchase(String paymentMethod, User client) {
        this.setPaymentMethod(paymentMethod);
        this.setClient(client);
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
}
