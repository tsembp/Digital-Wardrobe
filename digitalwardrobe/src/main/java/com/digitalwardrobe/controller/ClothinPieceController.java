package com.digitalwardrobe.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalwardrobe.models.ClothingPiece;
import com.digitalwardrobe.service.ClothingPieceService;

@RestController
@RequestMapping("/api/clothing")
public class ClothinPieceController {

    @Autowired
    private ClothingPieceService clothingPieceService;

    @PostMapping
    public ResponseEntity<ClothingPiece> addClothingPiece(@RequestBody ClothingPiece clothingPiece, Principal principal) {
        return ResponseEntity.ok(clothingPieceService.addClothingPiece(clothingPiece, principal.getName()));
    } // tested OK

    @GetMapping
    public ResponseEntity<List<ClothingPiece>> getAllClothingPieces(Principal principal) {
        return ResponseEntity.ok(clothingPieceService.getAllClothingPieces(principal.getName()));
    } // tested OK

    @GetMapping("/{id}")
    public ResponseEntity<ClothingPiece> getClothingPiece(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(clothingPieceService.getClothingPiece(id, principal.getName()));
    } // tested OK

    @PutMapping("/{id}")
    public ResponseEntity<ClothingPiece> updateClothingPiece(@PathVariable Long id, @RequestBody ClothingPiece clothingPiece, Principal principal) {
        return ResponseEntity.ok(clothingPieceService.updateClothingPiece(id, clothingPiece, principal.getName()));
    } // tested OK

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClothingPiece(@PathVariable Long id, Principal principal) {
        clothingPieceService.deleteClothingPiece(id, principal.getName());
        return ResponseEntity.ok("Clothing piece with id=" + id + " deleted.");
    } // tested OK
    
}
