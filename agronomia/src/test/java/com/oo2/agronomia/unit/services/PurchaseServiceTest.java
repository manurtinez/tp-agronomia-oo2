package com.oo2.agronomia.unit.services;

import com.oo2.agronomia.models.Client;
import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.PurchaseRepository;
import com.oo2.agronomia.repositories.User.ClientRepository;
import com.oo2.agronomia.services.PurchaseService;
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
public class PurchaseServiceTest {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ClientRepository clientRepository;


    @Test
    public void createPurchaseTest() {
        // Para crear un purchase, debe existir un usuario previamente
        User newUser = clientRepository.save(new Client("manu", "manu@manu.com", "1234", "address1"));

        purchaseService.addPurchase("payment1", newUser);
        List<Purchase> purchaseList = purchaseService.findAll();
        assertEquals(1, purchaseList.size());

        Purchase pur = purchaseList.get(0);
        assertEquals("payment1", pur.getPaymentMethod());
        assertEquals("manu", pur.getClient().getName());
        assertEquals(1, pur.getClient().getPurchases().size());
    }

    @Test
    public void getAllPurchasesTest() {
        // Para crear un purchase, debe existir un usuario previamente
        User newUser = clientRepository.save(new Client("manu", "manu@manu.com", "1234", "address1"));
        User newUser2 = clientRepository.save(new Client("manu2", "manu2@manu.com", "1234", "address2"));

        purchaseService.addPurchase("payment1", newUser);
        purchaseService.addPurchase("payment2", newUser2);
        purchaseService.addPurchase("payment3", newUser);


        List<Purchase> purchaseList = purchaseService.findAll();
        assertEquals(3, purchaseList.size());
    }
}
