package com.example.springdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @NotBlank(message = "fname is required")
  private String fname;

  @NotBlank(message = "lname is required")
  private String lname;

  @NotBlank(message = "email is required")
  private String email;

  @NotBlank(message = "role is required")
  private String role;

  @NotBlank(message = "password is required")
  private String password;

  @Column(insertable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  public User() {}

  public User(Long id, String fname, String lname, String email, String role, String password) {
    this.id = id;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.role = role;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }

  public String getLname() {
    return lname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
