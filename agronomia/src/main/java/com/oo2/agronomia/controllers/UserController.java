package com.oo2.agronomia.controllers;

import java.util.List;

import com.oo2.agronomia.models.User;
import com.oo2.agronomia.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private UserService userService;

    @PostMapping(path = "/nuevo")
    public ResponseEntity<User> addNewUser(@RequestParam String name, @RequestParam String email) {
        User newUser = userService.addUser(name, email);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/todos")
    public @ResponseBody List<User> getAllUsers() {
        return userService.findAll();
    }
}
