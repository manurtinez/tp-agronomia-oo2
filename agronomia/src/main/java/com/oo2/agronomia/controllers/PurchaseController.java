package com.oo2.agronomia.controllers;

import java.util.List;

import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.services.PurchaseService;
import com.oo2.agronomia.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        System.out.println(client);

        Purchase newPurchase = purchaseService.addPurchase(paymentMethod, client);
        System.out.println("entra a controller");
        System.out.println(newPurchase);
        return new ResponseEntity<Purchase>(newPurchase, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<Purchase> getAllPurchases() {
        return purchaseService.findAll();
    }
}
