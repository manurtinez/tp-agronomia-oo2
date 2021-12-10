package com.oo2.agronomia.unit.services;

import com.oo2.agronomia.models.*;
import com.oo2.agronomia.models.strategy.PersonalStrategy;
import com.oo2.agronomia.repositories.Product.SingleProductRepository;
import com.oo2.agronomia.repositories.PurchaseRepository;
import com.oo2.agronomia.repositories.StrategyRepository;
import com.oo2.agronomia.repositories.User.ClientRepository;
import com.oo2.agronomia.services.PurchaseService;
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
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StrategyRepository strategyRepository;

    @Autowired
    private SingleProductRepository singleProductRepository;

    @Test
    public void createPersonalPurchaseTest() {
        // Para crear un purchase, debe existir un usuario previamente
        User newUser = clientRepository.save(new Client("manu", "manu@manu.com", "1234", "address1"));

        SingleProduct singleProd = new SingleProduct("zanahoria", "verdura", 10);
        singleProductRepository.save(singleProd);
        SingleProduct singleProd2 = new SingleProduct("zanahoria", "verdura", 10);
        singleProductRepository.save(singleProd2);
        List<Product> productList = new ArrayList<Product>();
        productList.add(singleProd);
        productList.add(singleProd2);

        PersonalStrategy strat = strategyRepository.save(new PersonalStrategy());

        purchaseService.addPersonalPurchase("payment1", newUser, productList);
        List<Purchase> purchaseList = purchaseService.findAll();
        assertEquals(1, purchaseList.size());

        Purchase pur = purchaseList.get(0);
        assertEquals("payment1", pur.getPaymentMethod());
        assertEquals("manu", pur.getClient().getName());
        assertEquals(1, pur.getClient().getPurchases().size());
    }

    @Test
    public void getAllPersonalPurchasesTest() {
        // Para crear un purchase, debe existir un usuario previamente
        User newUser = clientRepository.save(new Client("manu", "manu@manu.com", "1234", "address1"));
        User newUser2 = clientRepository.save(new Client("manu2", "manu2@manu.com", "1234", "address2"));

        SingleProduct singleProd = new SingleProduct("zanahoria", "verdura", 10);
        singleProductRepository.save(singleProd);
        SingleProduct singleProd2 = new SingleProduct("zanahoria", "verdura", 10);
        singleProductRepository.save(singleProd2);
        List<Product> productList = new ArrayList<Product>();
        productList.add(singleProd);
        productList.add(singleProd2);

        PersonalStrategy strat = strategyRepository.save(new PersonalStrategy());

        Purchase pur1 = purchaseService.addPersonalPurchase("payment1", newUser, productList);
        Purchase pur2 = purchaseService.addPersonalPurchase("payment2", newUser2, productList);
        Purchase pur3 = purchaseService.addPersonalPurchase("payment3", newUser, productList);


        List<Purchase> purchaseList = (List<Purchase>) purchaseRepository.findAll();
        assertEquals(3, purchaseList.size());
    }
}
