package com.example.springdemo.service;

import com.example.springdemo.helper.Exceptions;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User get(Long id) throws Exceptions.UserNotFoundException {
       return userRepository.findById(id).orElseThrow(() -> new Exceptions.UserNotFoundException("User not found!!"));
    }

    public User create(User user) throws Exceptions.UserExistsException {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new Exceptions.UserExistsException("User already exists!!");
        }
        return userRepository.save(user);
    }

    public User edit(Long id, User user) throws Exceptions.UserNotFoundException {
        if(userRepository.existsById(id)){
            userRepository.edit(user.getFname(), user.getLname(), user.getEmail(), String.valueOf(user.getRole()), id);
            return userRepository.findById(id).orElseThrow(
                    () -> new Exceptions.UserNotFoundException("User not found!!"));
        }else {
            throw new Exceptions.UserNotFoundException("User not found!!");
        }
    }

    public void delete(Long id) {
      userRepository.deleteById(id);
    }

}
