package com.oo2.agronomia.repositories.User;

import com.oo2.agronomia.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository<T extends User> extends CrudRepository<User, Integer> {

    User findByName(String manu);

    User findByEmail(String email);
}
