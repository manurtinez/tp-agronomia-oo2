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
        SingleProduct singleProd = new SingleProduct("zanahoria", "verdura", 10);
        productService.addSingleProduct(singleProd);
        SingleProduct singleProd2 = new SingleProduct("manzana", "fruta", 10);
        productService.addSingleProduct(singleProd2);

        List<SingleProduct> productList = productService.findAllSingle();
        assertEquals(2, productList.size());
    }

    @Test
    public void createBolsonTest() {
        // El bolson esta compuesto de varios productos simples
        SingleProduct singleProd = new SingleProduct("zanahoria", "verdura", 10);
        SingleProduct singleProd2 = new SingleProduct("manzana", "fruta", 10);
        productService.addSingleProduct(singleProd);
        productService.addSingleProduct(singleProd2);

        Bolson bolson = new Bolson("bolson1");
        bolson.addProduct(singleProd);
        bolson.addProduct(singleProd2);

        productService.addBolson(bolson);

        List<Bolson> bolsonList = productService.findAllBolson();
        assertEquals(1, bolsonList.size());

        Bolson bol = bolsonList.get(0);
        assertEquals("bolson1", bol.getName());
    }


}
