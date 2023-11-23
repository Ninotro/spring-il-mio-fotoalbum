package com.experis.course.spring.repository;

import com.experis.course.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Creo metodo per ritornare uno User cercandolo per email
    Optional<User> findByEmail(String email);
}