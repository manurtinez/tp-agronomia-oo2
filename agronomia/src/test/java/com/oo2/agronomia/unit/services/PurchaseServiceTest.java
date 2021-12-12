package com.oo2.agronomia.unit.services;

import com.oo2.agronomia.models.*;
import com.oo2.agronomia.repositories.User.ClientRepository;
import com.oo2.agronomia.services.ProductService;
import com.oo2.agronomia.services.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class PurchaseServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void initEach() {
        // Para crear un purchase, debe existir un usuario previamente. Creo 2
        clientRepository.save(new Client("manu", "manu@manu.com", "1234", "address1"));
        clientRepository.save(new Client("manu2", "manu2@manu.com", "1234", "address2"));

        // Creo varios productos para usar en purchases
        SingleProduct prod1 = productService.addSingleProduct(new SingleProduct("zanahoria", "verdura", 10));
        SingleProduct prod2 = productService.addSingleProduct(new SingleProduct("manzana", "fruta", 20));

        // Creo un bolson
        Bolson bol = new Bolson("bolson1");
        bol.addProduct(prod1);
        bol.addProduct(prod2);
        productService.addBolson(bol);
    }

    @Test
    public void createPurchaseTest() {
        // Traigo cualquier usuario para crear una compra
        User newUser = clientRepository.findByName("manu");

        // Creo la lista de productos para la purchase
        List<Product> productList = new ArrayList<Product>();
        SingleProduct prod1 = (SingleProduct) productService.findByName("zanahoria");
        Bolson bol = (Bolson) productService.findByName("bolson1");
        productList.add(prod1);
        productList.add(bol);

        // Creo la purchase con la lista de productos
        Purchase newPurchase = purchaseService.addPurchase("payment1", newUser, productList);

        // Compruebo que la compra que se creo tiene los datos esperados
        assertEquals("payment1", newPurchase.getPaymentMethod());
        assertEquals("manu", newPurchase.getClient().getName());

        // La compra tiene 3 productos, uno simple y 2 dentro de un bolson
        assertEquals(3, newPurchase.getTotalOfProducts());

        // Y que fue agregada al array de compras del usuario
        assertEquals(1, newPurchase.getClient().getPurchases().size());
    }

    @Test
    public void getAllPurchasesTest() {
        // Traigo la lista de usuarios para crear compras
        List<User> clientList = (List<User>) clientRepository.findAll();

        // Creo la lista de productos para la purchase
        List<Product> productList = new ArrayList<Product>();
        SingleProduct prod1 = (SingleProduct) productService.findByName("zanahoria");
        Bolson bol = (Bolson) productService.findByName("bolson1");
        productList.add(prod1);
        productList.add(bol);

        // Agrego 2 compras para cada usuario
        for (int i = 0; i < clientList.size(); i++) {
            purchaseService.addPurchase("payment" + i, clientList.get(i), productList);
            purchaseService.addPurchase("payment" + i, clientList.get(i), productList);
        }

        // Compruebo que el total de compras es 4 (2 * 2 usuarios)
        List<Purchase> purchaseList = purchaseService.findAll();
        assertEquals(4, purchaseList.size());

        // Compruebo que cada usuario tiene 2 compras en su listado de compras
        purchaseList.forEach(purchase -> assertEquals(2, purchase.getClient().getPurchases().size()));
    }
}
