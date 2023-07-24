package com.example.springdemo.service;

import com.example.springdemo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    List<User> users = List.of(new User(1, "John", "Doe", "johndoe@gmail.com", "Admin", "azx123"));

    public List<User> getAll() {
        return users;
    }

    public User get(int id) {
        User user = null;
        for(User u: users) {
            if(u.getId() == id) {
                user = u;
            }
        }
        return user;
    }

    public User create(User user) {
        users.add(user);
        return user;
    }

    public User edit(int id, User user) {
        for(User u: users) {
            if(u.getId() == id) {
               users.remove(u);
               users.add(user);
            }
        }
        return user;
    }

    public void delete(int id) {
        for(User u: users) {
            if(u.getId() == id) {
                users.remove(u);
            }
        }
    }
}