package com.digitalwardrobe.service;

import com.digitalwardrobe.exception.ResourceNotFoundException;
import com.digitalwardrobe.models.ClothingPiece;
import com.digitalwardrobe.models.Outfit;
import com.digitalwardrobe.models.User;
import com.digitalwardrobe.repository.OutfitRepository;
import com.digitalwardrobe.repository.UserRepository;
import com.digitalwardrobe.repository.ClothingPieceRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class OutfitService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OutfitRepository outfitRepository;

    @Autowired
    private ClothingPieceRepository clothingPieceRepository;

    // Create outfit
    public Outfit addOutfit(Outfit outfit, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        outfit.setUser(user);

        // Fetch and associate clothing pieces
        Set<ClothingPiece> clothingPieces = outfit.getClothingPieceIds().stream()
            .map(id -> clothingPieceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Clothing piece not found with id: " + id)))
            .collect(Collectors.toSet());
        outfit.setClothingPieces(clothingPieces);
        return outfitRepository.save(outfit);
    }

    // Get user's outfits
    public List<Outfit> getAllOutfits(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Outfit> outfits = outfitRepository.findByUser(user);

        // Populate clothingPieceIds for each outfit
        outfits.forEach(outfit -> {
            Set<Long> clothingPieceIds = outfit.getClothingPieces().stream()
                .map(ClothingPiece::getId)
                .collect(Collectors.toSet());
            outfit.setClothingPieceIds(clothingPieceIds);
        });

        return outfits;
    }

    // Get specific outfit
    public Outfit getOutfit(Long id, String username) {
        return outfitRepository.findByIdAndUserUsername(id, username).orElseThrow(() -> new ResourceNotFoundException("Outfit not found"));
    }

    // Update outfit
    public Outfit updateOutfit(Long id, Outfit outfit, String username) {
        Outfit existing = getOutfit(id, username);
        existing.setName(outfit.getName());
        existing.setClothingPieces(outfit.getClothingPieces());
        return outfitRepository.save(existing);
    }

    // Delete outfit
    public void deleteOutfit(Long id, String username) {
        Outfit existing = getOutfit(id, username);
        outfitRepository.delete(existing);
    }
}
