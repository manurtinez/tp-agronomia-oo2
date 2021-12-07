package com.oo2.agronomia.repositories;

import java.util.List;

import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.User;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
    public List<Purchase> findByClient(User client);
}
