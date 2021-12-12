package com.oo2.agronomia.unit.services;

import com.oo2.agronomia.models.Client;
import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.PurchaseRepository;
import com.oo2.agronomia.repositories.User.ClientRepository;
import com.oo2.agronomia.services.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void initEach() {
        // Para crear un purchase, debe existir un usuario previamente. Creo 2
        clientRepository.save(new Client("manu", "manu@manu.com", "1234", "address1"));
        clientRepository.save(new Client("manu2", "manu2@manu.com", "1234", "address2"));
    }

    @Test
    public void createPurchaseTest() {
        // Traigo cualquier usuario para crear una compra
        User newUser = clientRepository.findByName("manu");
        Purchase newPurchase = purchaseService.addPurchase("payment1", newUser);

        // Compruebo que la compra que se creo tiene los datos esperados
        assertEquals("payment1", newPurchase.getPaymentMethod());
        assertEquals("manu", newPurchase.getClient().getName());
        // Y que fue agregada al array de compras del usuario
        assertEquals(1, newPurchase.getClient().getPurchases().size());
    }

    @Test
    public void getAllPurchasesTest() {
        // Traigo la lista de usuarios para crear compras
        List<User> clientList = (List<User>) clientRepository.findAll();

        // Agrego 2 compras para cada usuario
        for (int i = 0; i < clientList.size(); i++) {
            purchaseService.addPurchase("payment" + i, clientList.get(i));
            purchaseService.addPurchase("payment" + i, clientList.get(i));
        }

        // Compruebo que el total de compras es 4 (2 * 2 usuarios)
        List<Purchase> purchaseList = purchaseService.findAll();
        assertEquals(4, purchaseList.size());

        // Compruebo que cada usuario tiene 2 compras en su listado de compras
        purchaseList.forEach(purchase -> assertEquals(2, purchase.getClient().getPurchases().size()));
    }
}
