package com.example.springdemo.web;

import com.example.springdemo.model.User;
import com.example.springdemo.service.BCryptService;
import com.example.springdemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private UserService service;

    private BCryptService bCryptService;
    public UserController(UserService userService, BCryptService bCryptService) {
        this.service = userService;
        this.bCryptService = bCryptService;
    }

    @PostMapping("/signup")
    public User signup(@Valid @RequestBody User user) {
        user.setId(service.getAll().size());
        user.setPassword(bCryptService.hash(user.getPassword()));
        return service.create(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAll();
    }
}
