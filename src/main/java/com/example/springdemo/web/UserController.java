package com.example.springdemo.web;

import com.example.springdemo.model.User;
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
    public UserController(UserService userService) {
        this.service = userService;
    }

    @PostMapping("/signup")
    public User signup(@Valid @RequestBody User user) {
        user.setId(service.getAll().size());
        return service.create(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAll();
    }
}
