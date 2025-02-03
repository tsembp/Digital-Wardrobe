package com.digitalwardrobe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalwardrobe.exception.ResourceNotFoundException;
import com.digitalwardrobe.models.*;
import com.digitalwardrobe.repository.ClothingPieceRepository;
import com.digitalwardrobe.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;

@Service
public class ClothingPieceService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClothingPieceRepository clothingPieceRepository;

    // Add new clothing piece
    public ClothingPiece addClothingPiece(ClothingPiece clothingPiece, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        clothingPiece.setUser(user); // assign to user if found
        return clothingPieceRepository.save(clothingPiece); // save clothing piece
    }

    // Get user's clothes
    public List<ClothingPiece> getAllClothingPieces(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("User found: " + user.getUsername());
        return clothingPieceRepository.findByUser(user); // get all clothing pieces for user
    }

    // Get specific clothing piece
    public ClothingPiece getClothingPiece(Long id, String username) {
        return clothingPieceRepository.findByIdAndUserUsername(id, username).orElseThrow(() -> new ResourceNotFoundException("Clothing piece not found"));
    }

    // Update clothing piece
    public ClothingPiece updateClothingPiece(Long id, ClothingPiece clothingPiece, String username) {
        ClothingPiece existing = getClothingPiece(id, username);
        existing.setName((String)clothingPiece.getName());
        existing.setCategory((Category)clothingPiece.getCategory());
        existing.setClothingPieceImgUrl(clothingPiece.getClothingPieceImgUrl());
        return clothingPieceRepository.save(existing);
    }

    // Delete clothing piece
    public void deleteClothingPiece(Long id, String username) {
        ClothingPiece existing = getClothingPiece(id, username);

        // Remove associations with outfits
        for (Outfit outfit : existing.getOutfits()) {
            outfit.getClothingPieces().remove(existing);
        }
        // clothingPieceRepository.save(existing);

        clothingPieceRepository.delete(existing);
    }
}
