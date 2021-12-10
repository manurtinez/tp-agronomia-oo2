package com.oo2.agronomia.controllers;

import com.oo2.agronomia.models.Client;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/nuevo-cliente")
    public ResponseEntity<Client> addNewClient(@RequestParam String name, @RequestParam String email,
                                               @RequestParam String password, @RequestParam String address) {
        Client newUser = userService.addClient(name, email, password, address);
        return new ResponseEntity<Client>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    List<User> getAllUsers() {
        return userService.findAll();
    }
}
