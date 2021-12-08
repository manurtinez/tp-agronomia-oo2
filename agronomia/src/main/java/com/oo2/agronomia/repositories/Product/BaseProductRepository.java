package com.oo2.agronomia.repositories.Product;

import com.oo2.agronomia.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseProductRepository<T extends Product> extends CrudRepository<T, Integer> {
}
