package com.oo2.agronomia.services;

import java.util.List;

import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.PurchaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public PurchaseService() {
    }

    public List<Purchase> findAll() {
        return (List<Purchase>) purchaseRepository.findAll();
    }

    public Purchase addPurchase(String paymentMethod, User client) {
        System.out.println("entra a service");

        Purchase newPurchase = new Purchase(paymentMethod, client);
        System.out.println(newPurchase);
        return purchaseRepository.save(newPurchase);
    }
}
