package com.example.springdemo.web;

import com.example.springdemo.helper.Exceptions;
import com.example.springdemo.model.User;
import com.example.springdemo.service.BCryptService;
import com.example.springdemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService service;

    private final BCryptService bCryptService;
    public UserController(UserService userService, BCryptService bCryptService) {
        this.service = userService;
        this.bCryptService = bCryptService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody User user) {
        user.setPassword(bCryptService.hash(user.getPassword()));
        try {
            return new ResponseEntity<>(service.create(user), HttpStatus.OK);
        } catch (Exceptions.UserExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody LoginInformation loginInformation) {
        return null;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.get(id), HttpStatus.OK);
        } catch (Exceptions.UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> edit(@PathVariable Long id, @RequestBody User user) {
        try {
            return new ResponseEntity<>(service.edit(id, user), HttpStatus.OK);
        } catch (Exceptions.UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }

    static class LoginInformation {
        private String email;
        private String password;
    }
}
