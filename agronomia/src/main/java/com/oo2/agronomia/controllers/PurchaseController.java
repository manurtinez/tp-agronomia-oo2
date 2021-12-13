package com.oo2.agronomia.controllers;

import com.oo2.agronomia.models.*;
import com.oo2.agronomia.services.ProductService;
import com.oo2.agronomia.services.PurchaseService;
import com.oo2.agronomia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/new")
    public ResponseEntity<Purchase> addNewPurchase(@RequestParam String paymentMethod,
                                                   @RequestParam String clientEmail) {
        User client = userService.findByEmail(clientEmail);

        // TODO Por ahora esto HARDCODEADO, se recibiria la lista por RequestParam
        List<Product> productList = new ArrayList<Product>();
        SingleProduct prod1 = (SingleProduct) productService.findByName("zanahoria");
        Bolson bol = (Bolson) productService.findByName("bolson1");
        productList.add(prod1);
        productList.add(bol);

        Purchase newPurchase = purchaseService.addPurchase(paymentMethod, client, productList);
        return new ResponseEntity<Purchase>(newPurchase, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<Purchase> getAllPurchases() {
        return purchaseService.findAll();
    }
}
