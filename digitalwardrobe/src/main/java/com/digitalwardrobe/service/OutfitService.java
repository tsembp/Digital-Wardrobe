package com.digitalwardrobe.service;

import com.digitalwardrobe.exception.ResourceNotFoundException;
import com.digitalwardrobe.models.Outfit;
import com.digitalwardrobe.models.User;
import com.digitalwardrobe.repository.OutfitRepository;
import com.digitalwardrobe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class OutfitService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OutfitRepository outfitRepository;

    // Create outfit
    public Outfit addOutfit(Outfit outfit, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        outfit.setUser(user);
        return outfitRepository.save(outfit);
    }

    // Get user's outfits
    public List<Outfit> getAllOutfits(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // for debugging
        List<Outfit> outfits = outfitRepository.findByUser(user);
        System.out.println("Printing outfits");
        for(Outfit outfit : outfits) {
            System.out.println(outfit.getName());
        }
        return outfits;
        // return outfitRepository.findByUser(user);
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
