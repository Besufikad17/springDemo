package com.example.springdemo.repository;

import com.example.springdemo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update user set fname=?, lname=?, email=?, role=? where id=?", nativeQuery = true)
    void edit(String fname, String lname, String email, String role, Long userId);
}
