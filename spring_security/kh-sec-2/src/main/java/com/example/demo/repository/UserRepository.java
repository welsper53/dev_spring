package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD메소드는 JpaRepository가 제공한다
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);

}
