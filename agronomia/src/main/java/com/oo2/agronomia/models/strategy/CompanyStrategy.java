package com.oo2.agronomia.models.strategy;

import com.oo2.agronomia.models.Product;

import java.util.List;

public class CompanyStrategy extends PurchaseStrategy {
    // Para las empresas, el precio total se multiplica con un arancel de 50%
    @Override
    public double calculatePurchasePrice(List<Product> prods) {
        return prods.stream().mapToDouble(prod -> prod.getPrice() * 1.5).sum();
    }
}
