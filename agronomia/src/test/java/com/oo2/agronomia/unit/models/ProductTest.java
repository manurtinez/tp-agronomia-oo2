package com.oo2.agronomia.unit.models;

import com.oo2.agronomia.models.Bolson;
import com.oo2.agronomia.models.SingleProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductTest {
    @Test
    public void getProductPriceTest() {
        // Primero pruebo con un producto individual
        SingleProduct sProd1 = new SingleProduct("zanahoria", "verdura", 12.5);
        // Deberia devolver el precio individual
        assertEquals(12.5, sProd1.getPrice());

        // Ahora creo un producto bolson (compuesto)
        SingleProduct sProd2 = new SingleProduct("manzana", "fruta", 10);
        SingleProduct sProd3 = new SingleProduct("banana", "fruta", 3.2);
        Bolson bolson = new Bolson("bolson1");
        bolson.addProduct(sProd1);
        bolson.addProduct(sProd2);
        bolson.addProduct(sProd3);

        // Deberia devolver el precio combinado de todos los productos (25.7)
        assertEquals(25.7, bolson.getPrice());
    }
}
