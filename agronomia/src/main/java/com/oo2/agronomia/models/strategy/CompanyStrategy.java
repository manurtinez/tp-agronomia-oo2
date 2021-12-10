package com.oo2.agronomia.models.strategy;

import com.oo2.agronomia.models.Product;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class CompanyStrategy extends PurchaseStrategy {

//    @Override
//    public String getStrategy() {
//        return getClass().getSimpleName();
//    }

    // Para las empresas, el precio total se multiplica con un arancel de 50%
    @Override
    public double calculatePurchasePrice(List<Product> prods) {
        return prods.stream().mapToDouble(prod -> prod.getPrice() * 1.5).sum();
    }
}
