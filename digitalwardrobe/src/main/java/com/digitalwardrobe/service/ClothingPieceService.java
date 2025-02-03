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

    @Autowired
    private FileStorageService fileStorageService;

    // Add new clothing piece
    public ClothingPiece addClothingPiece(ClothingPiece clothingPiece, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        clothingPiece.setUser(user); // assign to user if found
        return clothingPieceRepository.save(clothingPiece); // save clothing piece
    }

    // Get user's clothes
    public List<ClothingPiece> getAllClothingPieces(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("User found: " + user.getUsername());
        return clothingPieceRepository.findByUser(user); // get all clothing pieces for user
    }

    // Get specific clothing piece
    public ClothingPiece getClothingPiece(Long id, String username) {
        return clothingPieceRepository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Clothing piece not found"));
    }

    // Update clothing piece
    public ClothingPiece updateClothingPiece(Long id, ClothingPiece clothingPiece, String username) {
        ClothingPiece existing = getClothingPiece(id, username);
        existing.setName((String) clothingPiece.getName());
        existing.setCategory((Category) clothingPiece.getCategory());
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
        // Step 2: Extract file path from clothing piece (if exists)
        String fileUrl = existing.getClothingPieceImgUrl(); // Assuming this field stores the Firebase URL
        if (fileUrl != null && !fileUrl.isEmpty()) {
            String fileName = extractFileNameFromUrl(fileUrl);

            System.out.println("🔎 Extracted file to delete: " + fileName);

            // Step 3: Delete from Firebase Storage
            fileStorageService.listAllFiles();
            boolean deleted = fileStorageService.deleteFile(fileName);
            if (!deleted) {
                throw new RuntimeException("Failed to delete associated image from Firebase Storage.");
            }
        }

        clothingPieceRepository.delete(existing);
    }

    private String extractFileNameFromUrl(String fileUrl) {
        System.out.println("🌐 Extracting filename from URL: " + fileUrl);

        if (fileUrl.contains("?")) {
            fileUrl = fileUrl.substring(0, fileUrl.indexOf("?")); // Remove query parameters
        }

        // Handle Firebase encoding (Firebase encodes "/" as "%2F")
        if (fileUrl.contains("%2F")) {
            fileUrl = fileUrl.substring(fileUrl.lastIndexOf("%2F") + 3);
        } else {
            fileUrl = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        }

        System.out.println("📂 Extracted filename: " + fileUrl);
        return fileUrl;
    }

}
