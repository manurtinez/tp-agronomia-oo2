package com.oo2.agronomia.services;

import java.util.List;

import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserService() {
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User addUser(String name, String email, String password) {
        User newUser = new User(name, email, password);
        return userRepository.save(newUser);
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
