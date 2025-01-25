package com.digitalwardrobe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalwardrobe.dto.UpdateUserDTO;
import com.digitalwardrobe.dto.UserDTO;
import com.digitalwardrobe.models.User;
import com.digitalwardrobe.repository.UserRepository;
import com.digitalwardrobe.exception.DuplicateUsernameException;
import com.digitalwardrobe.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get user/users
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return convertToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // public List<UserDTO> searchUsers(String query) {
    //     List<User> users = userRepository.searchUser(
    //         query, query, query);
    //     return users.stream()
    //                .map(this::convertToDTO)
    //                .collect(Collectors.toList());
    // }

    @Transactional
    public UserDTO updateUser(String username, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        
        if(updateUserDTO.getUsername() != null && !updateUserDTO.getUsername().equals(username)) {
            if(userRepository.findByUsername(updateUserDTO.getUsername()).isPresent()) { // check if username is already in-use
                throw new DuplicateUsernameException("Username '" + updateUserDTO.getUsername() + "' is already taken");
            }
            user.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
        }
        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    // Delete
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // Helper method to convert User model to DTO
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        return dto;
    }
}