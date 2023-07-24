package com.example.springdemo.model;

import jakarta.validation.constraints.NotBlank;

public class User {


  private int id;

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

  public User() {}

  public User(int id, String fname, String lname, String email, String role, String password) {
    this.id = id;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.role = role;
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
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
