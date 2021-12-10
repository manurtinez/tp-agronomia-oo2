package com.oo2.agronomia.models.strategy;

import com.oo2.agronomia.models.Product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * Esta clase es la base para las 3 estrategias a implementar
 */
@Entity
public abstract class PurchaseStrategy {
//    public String getStrategy();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public abstract double calculatePurchasePrice(List<Product> prods);
}
