package com.example.springdemo.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BCryptService {

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(11));
    }

    public boolean check(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
