package com.oo2.agronomia.services;

import com.oo2.agronomia.models.Product;
import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.models.strategy.PersonalStrategy;
import com.oo2.agronomia.repositories.PurchaseRepository;
import com.oo2.agronomia.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private StrategyRepository strategyRepository;

    public PurchaseService() {
    }

    public List<Purchase> findAll() {
        return (List<Purchase>) purchaseRepository.findAll();
    }

    public Purchase addPersonalPurchase(String paymentMethod, User client, List<Product> products) {
        PersonalStrategy strat = (PersonalStrategy) strategyRepository.findById(1).get();
        Purchase newPurchase = new Purchase(paymentMethod, client, products, strat);
        return purchaseRepository.save(newPurchase);
    }
}
