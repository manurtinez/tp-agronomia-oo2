package com.oo2.agronomia.repositories;

import com.oo2.agronomia.models.strategy.PurchaseStrategy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends CrudRepository<PurchaseStrategy, Integer> {
}
