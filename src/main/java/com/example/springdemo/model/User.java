package com.example.springdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @NotBlank(message = "fname is required")
  private String fname;

  @NotBlank(message = "lname is required")
  private String lname;

  @NotBlank(message = "email is required")
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  @NotBlank(message = "password is required")
  private String password;

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @CreationTimestamp
  private LocalDateTime createdAt;

  public User() {}

  public User(Long id, String fname, String lname, String email, String role, String password) {
    this.id = id;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.role = role.equals("user") ? Role.USER : Role.ADMIN;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = Role.valueOf(role);
  }

  public enum Role {
    USER, ADMIN
  }


  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(fname, user.fname) && Objects.equals(email, user.email) && Objects.equals(lname, user.lname) && Objects.equals(role, user.role) && Objects.equals(createdAt, user.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fname, lname, email, role, password, createdAt);
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + fname + " " + lname + '\'' +
            ", email='" + email + '\'' +
            ", role='" + role + '\'' +
            ", createdAt=" + createdAt +
            '}';
  }

}
