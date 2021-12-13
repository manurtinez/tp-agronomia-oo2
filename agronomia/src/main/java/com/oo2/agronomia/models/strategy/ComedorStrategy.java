package com.oo2.agronomia.models.strategy;

import com.oo2.agronomia.models.Product;

import java.util.List;

public class ComedorStrategy extends PurchaseStrategy {
    // Para los comedores, el precio total de cada producto se aplica un descuento, y luego se aplica un arancel comercial
    @Override
    public double calculatePurchasePrice(List<Product> prods) {
        return prods.stream().mapToDouble(prod -> prod.getPrice() * 0.7).sum() * 1.2;
    }
}
