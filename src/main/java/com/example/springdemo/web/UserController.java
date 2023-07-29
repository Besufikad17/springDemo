package com.example.springdemo.web;

import com.example.springdemo.helper.Exceptions;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.JwtService;
import com.example.springdemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.service = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String token = jwtService.generateToken(user);
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("user", service.create(user));
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exceptions.UserExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginInformation loginInformation) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginInformation.getEmail(),
                        loginInformation.getPassword()
                )
        );

        HashMap<String, Object> response = new HashMap<>();
        try {
            User user = userRepository.findByEmail(loginInformation.getEmail())
                    .orElseThrow(() -> new Exceptions.UserNotFoundException("User not found!!"));
            String token = jwtService.generateToken(user);
            response.put("user", user);
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exceptions.UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
