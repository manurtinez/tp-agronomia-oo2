package com.oo2.agronomia.controllers;

import com.oo2.agronomia.models.Bolson;
import com.oo2.agronomia.models.SingleProduct;
import com.oo2.agronomia.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(path = "/new-single")
    public ResponseEntity<SingleProduct> addNewSingleProduct(@RequestParam String name,
                                                             @RequestParam String type, @RequestParam double price) {
        SingleProduct createdProd = productService.addSingleProduct(new SingleProduct(name, type, price));
        return new ResponseEntity<SingleProduct>(createdProd, HttpStatus.CREATED);
    }

    @PostMapping(path = "/new-bolson")
    public ResponseEntity<Bolson> addNewBolson(@RequestParam String name) {
        Bolson createdProd = productService.addBolson(new Bolson(name));
        return new ResponseEntity<Bolson>(createdProd, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all-single")
    public @ResponseBody
    List<SingleProduct> getAllSingle() {
        return productService.findAllSingle();
    }

    @GetMapping(path = "/all-bolson")
    public @ResponseBody
    List<Bolson> getAllBolson() {
        return productService.findAllBolson();
    }
}
