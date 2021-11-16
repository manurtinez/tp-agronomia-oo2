package com.oo2.agronomia.controllers;

import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/nuevo")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
        User newUser = new User(name, email);
        userRepository.save(newUser);
        return "Guardado";
    }

    @GetMapping(path = "/todos")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
