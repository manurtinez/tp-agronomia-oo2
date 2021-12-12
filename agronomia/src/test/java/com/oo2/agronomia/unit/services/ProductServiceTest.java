package com.oo2.agronomia.unit.services;


import com.oo2.agronomia.models.Bolson;
import com.oo2.agronomia.models.SingleProduct;
import com.oo2.agronomia.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void createSingleProductTest() {
        // Intento crear un producto
        productService.addSingleProduct(new SingleProduct("zanahoria", "verdura", 10));

        // Intento traerlo y comprobar que es el correcto
        SingleProduct prod = (SingleProduct) productService.findByName("zanahoria");
        assertEquals("zanahoria", prod.getName());
        assertEquals("verdura", prod.getType());
        assertEquals(10, prod.getPrice());
    }

    @Test
    public void getAllSingleProdTest() {
        // Creo 3 productos simples
        productService.addSingleProduct(new SingleProduct("zanahoria", "verdura", 10));
        productService.addSingleProduct(new SingleProduct("manzana", "fruta", 20));
        productService.addSingleProduct(new SingleProduct("tomate", "comodín", 30));

        // Intento traer todos y compruebo que estan presentes
        List<SingleProduct> productList = productService.findAllSingle();
        assertEquals(3, productList.size());
    }

    @Test
    public void createBolsonTest() {
        // El bolson esta compuesto de varios productos simples
        productService.addSingleProduct(new SingleProduct("zanahoria", "verdura", 10));
        productService.addSingleProduct(new SingleProduct("manzana", "fruta", 20));

        // Traigo los productos y creo un bolson con ellos
        List<SingleProduct> productList = productService.findAllSingle();
        Bolson bolson = new Bolson("bolson1");
        productList.forEach(bolson::addProduct);

        // Creo el bolson
        productService.addBolson(bolson);

        // Traigo el bolson y compruebo que tenga los datos correctos
        Bolson bol = (Bolson) productService.findByName("bolson1");
        assertEquals("bolson1", bol.getName());
        assertEquals(2, bol.getProducts().size());
    }

    @Test
    public void getAllBolsonTest() {
        // Para fines de este test, puedo crear 3 bolsones con 1 producto c/u
        SingleProduct prod1 = productService.addSingleProduct(new SingleProduct("manzana", "fruta", 20));
        Bolson bol;
        for (int i = 0; i < 3; i++) {
            bol = productService.addBolson(new Bolson("bolson" + i));
            bol.addProduct(prod1);
        }

        // Traigo los bolsones y chequeo que esten correctos
        List<Bolson> bolsonList = productService.findAllBolson();
        assertEquals(3, bolsonList.size());
        assertEquals("manzana", bolsonList.get(0).getProducts().get(0).getName());
    }


}
