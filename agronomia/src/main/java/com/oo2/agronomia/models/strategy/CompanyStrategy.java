package com.oo2.agronomia.models.strategy;

import com.oo2.agronomia.models.Product;

import java.util.List;

public class CompanyStrategy extends PurchaseStrategy {
    // Para las empresas, el precio total se multiplica con un arancel de 50%
    @Override
    public double calculatePurchasePrice(List<Product> prods) {
        double sum = prods.stream().mapToDouble(prod -> prod.getPrice() * 1.5).sum();
        // Hay que redondear la suma para representar bien el numero de punto flotante
        return Math.round(sum * 100.0) / 100.0;
    }
}
