package com.digitalwardrobe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalwardrobe.dto.UpdateUserDTO;
import com.digitalwardrobe.dto.UserDTO;
import com.digitalwardrobe.exception.DuplicateUsernameException;
import com.digitalwardrobe.exception.ResourceNotFoundException;
import com.digitalwardrobe.models.User;
import com.digitalwardrobe.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<String> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("Hello, " + userDetails.getUsername() + "! This is your profile.");
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        UserDTO updatedUser = userService.updateUser(userDetails.getUsername(), updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(ex.getMessage());
    }
    
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsername(DuplicateUsernameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                           .body(ex.getMessage());
    }

    // @GetMapping("/search")
    // public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String query) {
    //     if (query == null || query.trim().isEmpty()) {
    //         return ResponseEntity.badRequest().build();
    //     }
    //     List<UserDTO> users = userService.searchUsers(query);
    //     return ResponseEntity.ok(users);
    // }

}