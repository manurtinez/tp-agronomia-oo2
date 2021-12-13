package com.oo2.agronomia.models.strategy;

import com.oo2.agronomia.models.Product;

import java.util.List;

/**
 * Esta clase es la base para las 3 estrategias a implementar
 */
public abstract class PurchaseStrategy {
    public abstract double calculatePurchasePrice(List<Product> prods);
}
