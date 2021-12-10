package com.oo2.agronomia.controllers;

import com.oo2.agronomia.models.Product;
import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.SingleProduct;
import com.oo2.agronomia.models.User;
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

    @PostMapping(path = "/new")
    public ResponseEntity<Purchase> addNewPurchase(@RequestParam String paymentMethod,
            @RequestParam String clientEmail) {
        User client = userService.findByEmail(clientEmail);

        // TODO SOLO DE PRUEBA
        SingleProduct singleProd = new SingleProduct("zanahoria", "verdura", 10);
        SingleProduct singleProd2 = new SingleProduct("zanahoria", "verdura", 10);
        List<Product> productList = new ArrayList<Product>();
        productList.add(singleProd);
        productList.add(singleProd2);
        // END TODO

        Purchase newPurchase = purchaseService.addPersonalPurchase(paymentMethod, client, productList);
        return new ResponseEntity<Purchase>(newPurchase, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<Purchase> getAllPurchases() {
        return purchaseService.findAll();
    }
}
