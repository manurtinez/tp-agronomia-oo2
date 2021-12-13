package com.oo2.agronomia.models.strategy;

import com.oo2.agronomia.models.Product;

import java.util.List;

public class PersonalStrategy extends PurchaseStrategy {
    /**
     * La estrategia para compra personal simplemente realiza la compra
     *
     * @param prods
     */
    @Override
    public double calculatePurchasePrice(List<Product> prods) {
        return prods.stream().mapToDouble(Product::getPrice).sum();
    }
}
