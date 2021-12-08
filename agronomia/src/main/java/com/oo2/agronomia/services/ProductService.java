package com.oo2.agronomia.services;

import com.oo2.agronomia.models.Bolson;
import com.oo2.agronomia.models.SingleProduct;
import com.oo2.agronomia.repositories.Product.BolsonRepository;
import com.oo2.agronomia.repositories.Product.SingleProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private BolsonRepository bolsonRepository;

    @Autowired
    private SingleProductRepository singleProductRepository;

    public void addSingleProduct(SingleProduct product) {
        singleProductRepository.save(product);
    }

    public List<SingleProduct> findAllSingle() {
        return (List<SingleProduct>) singleProductRepository.findAll();
    }

    public void addBolson(Bolson bolson) {
        bolsonRepository.save(bolson);
    }

    public List<Bolson> findAllBolson() {
        return (List<Bolson>) bolsonRepository.findAll();
    }
}
