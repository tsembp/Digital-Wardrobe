package com.digitalwardrobe.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.digitalwardrobe.models.Outfit;
import com.digitalwardrobe.service.OutfitService;

@RestController
@RequestMapping("/api/outfit")
public class OutfitController {
    
    @Autowired
    private OutfitService outfitService;

    @PostMapping
    public ResponseEntity<Outfit> addOutfit(@RequestBody Outfit outfit, Principal principal) {
        return ResponseEntity.ok(outfitService.addOutfit(outfit, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<Outfit>> getAllOutfits(Principal principal) {
        return ResponseEntity.ok(outfitService.getAllOutfits(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Outfit> getOutfit(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(outfitService.getOutfit(id, principal.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Outfit> updateOutfit(@PathVariable Long id, @RequestBody Outfit outfit, Principal principal) {
        return ResponseEntity.ok(outfitService.updateOutfit(id, outfit, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutfit(@PathVariable Long id, Principal principal) {
        outfitService.deleteOutfit(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}