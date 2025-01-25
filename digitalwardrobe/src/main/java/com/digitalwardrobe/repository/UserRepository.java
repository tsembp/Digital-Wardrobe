package com.digitalwardrobe.repository;

import com.digitalwardrobe.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by id
    Optional<User> findById(String id);

    // // Search for user based on username, firstName or lastname
    // List<User> searchUser(String username, String firstName, String lastName);
}