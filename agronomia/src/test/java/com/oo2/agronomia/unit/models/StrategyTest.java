package com.oo2.agronomia.unit.models;

import com.oo2.agronomia.models.Bolson;
import com.oo2.agronomia.models.Product;
import com.oo2.agronomia.models.SingleProduct;
import com.oo2.agronomia.models.strategy.ComedorStrategy;
import com.oo2.agronomia.models.strategy.CompanyStrategy;
import com.oo2.agronomia.models.strategy.PersonalStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StrategyTest {

    static SingleProduct prod1 = new SingleProduct("manzana", "fruta", 10);
    static SingleProduct prod2 = new SingleProduct("banana", "fruta", 3.2);
    static SingleProduct prod3 = new SingleProduct("tomate", "comodín", 5.3);
    static Bolson bolson = new Bolson("bolson1");
    static List<Product> productList = new ArrayList<Product>();

    @BeforeAll
    static void init() {
        // Antes de iniciar los tests, cargo los productos en bolson y lista
        bolson.addProduct(prod1);
        bolson.addProduct(prod2);

        productList.add(bolson);
        productList.add(prod3);
    }

    @Test
    public void personalStrategyTest() {
        // Creo una estrategia de tipo "personal"
        PersonalStrategy strat = new PersonalStrategy();

        // Intento llamar al metodo "calculatePurchasePrice" de los strategy.
        // El precio para Personal deberia ser exactamente la suma de los productos
        assertEquals(18.5, strat.calculatePurchasePrice(productList));
    }

    @Test
    public void companyStrategyTest() {
        // Creo una estrategia de tipo "personal"
        CompanyStrategy strat = new CompanyStrategy();

        // Intento llamar al metodo "calculatePurchasePrice" de los strategy.
        // El precio para Company deberia ser la suma de los productos SUMANDO un 50% adicional
        assertEquals(27.75, strat.calculatePurchasePrice(productList));
    }

    @Test
    public void comedorStrategyTest() {
        // Creo una estrategia de tipo "personal"
        ComedorStrategy strat = new ComedorStrategy();

        // Intento llamar al metodo "calculatePurchasePrice" de los strategy.
        // La formula es: sum(prod * 0.7) + 20%
        assertEquals(15.54, strat.calculatePurchasePrice(productList));
    }
}
