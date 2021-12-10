package com.oo2.agronomia.models.strategy;

import com.oo2.agronomia.models.Product;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class PersonalStrategy extends PurchaseStrategy {

//    @Override
//    public String getStrategy() {
//        return getClass().getSimpleName();
//    }

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
