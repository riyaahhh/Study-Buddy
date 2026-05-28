package com.studybuddy.studybuddy_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studybuddy.studybuddy_backend.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // finds user by email — used in login
    Optional<User> findByEmail(String email);

    // checks if email already exists — used in signup
    boolean existsByEmail(String email);
}