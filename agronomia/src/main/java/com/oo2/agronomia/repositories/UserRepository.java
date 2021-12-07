package com.oo2.agronomia.repositories;

import com.oo2.agronomia.models.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByName(String manu);

    User findByEmail(String email);
}
