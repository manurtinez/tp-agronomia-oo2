package com.oo2.agronomia.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oo2.agronomia.controllers.PurchaseController;
import com.oo2.agronomia.models.Product;
import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.SingleProduct;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.models.strategy.PersonalStrategy;
import com.oo2.agronomia.services.PurchaseService;
import com.oo2.agronomia.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PurchaseService purchaseService;

    @MockBean
    private UserService userService;

    @Test
    void getAllPurchases() throws Exception {
        List<Purchase> purchases = new ArrayList<Purchase>();

        User user1 = new User("manu", "manu@manu.com", "1234");
        User user2 = new User("manu2", "manu2@manu.com", "1234");

        SingleProduct singleProd = new SingleProduct("zanahoria", "verdura", 10);
        SingleProduct singleProd2 = new SingleProduct("zanahoria", "verdura", 10);
        List<Product> productList = new ArrayList<Product>();
        productList.add(singleProd);
        productList.add(singleProd2);

        purchases.add(new Purchase("payment1", user1, productList, new PersonalStrategy()));
        purchases.add(new Purchase("payment2", user2, productList, new PersonalStrategy()));
        assertEquals(user1, purchases.get(0).getClient());

        when(purchaseService.findAll()).thenReturn(purchases);

        mockMvc.perform(get("/purchase/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].paymentMethod", is("payment1")));
    }

    @Test
    void addNewPurchaseTest() throws Exception {
        User newUser = new User("manu", "manu@manu.com", "1234");

        SingleProduct singleProd = new SingleProduct("zanahoria", "verdura", 10);
        SingleProduct singleProd2 = new SingleProduct("zanahoria", "verdura", 10);
        List<Product> productList = new ArrayList<Product>();
        productList.add(singleProd);
        productList.add(singleProd2);

        Purchase newPurchase = new Purchase("payment1", newUser, productList, new PersonalStrategy());

        when(purchaseService.addPersonalPurchase(newPurchase.getPaymentMethod(), newPurchase.getClient(), newPurchase.getProducts()))
                .thenReturn(newPurchase);
        when(userService.findByEmail("manu@manu.com")).thenReturn(newUser);

        MockHttpServletResponse response = mockMvc
                .perform(post("/purchase/new").contentType("application/json").param("paymentMethod",
                                newPurchase.getPaymentMethod())
                        .param("clientEmail", newUser.getEmail())
                        .content(objectMapper.writeValueAsString(newPurchase)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertEquals(201, response.getStatus());
        Purchase assertPurchase = objectMapper.readValue(response.getContentAsString(),
                Purchase.class);
        assertEquals(newPurchase.getPaymentMethod(), assertPurchase.getPaymentMethod());
        assertEquals(newPurchase.getClient().getEmail(), assertPurchase.getClient().getEmail());
    }
}
