package com.oo2.agronomia.services;

import com.oo2.agronomia.models.Product;
import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public PurchaseService() {
    }

    public List<Purchase> findAll() {
        return (List<Purchase>) purchaseRepository.findAll();
    }

    public Purchase addPurchase(String paymentMethod, User client, List<Product> productList) {
        Purchase newPurchase = new Purchase(paymentMethod, client, productList);
        return purchaseRepository.save(newPurchase);
    }
}
