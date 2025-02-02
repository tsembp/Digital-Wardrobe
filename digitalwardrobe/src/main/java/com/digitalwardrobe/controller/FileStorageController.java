package com.digitalwardrobe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.digitalwardrobe.models.ClothingPiece;
import com.digitalwardrobe.repository.ClothingPieceRepository;
import com.digitalwardrobe.service.FileResponse;
import com.digitalwardrobe.service.FileStorageService;

@RestController
@RequestMapping("/api/files")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ClothingPieceRepository clothingPieceRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("text") Long clothingPieceId) {
        try {
            String imageUrl = fileStorageService.uploadFile(file);
            
            // Fetch ClothingPiece from the database
            ClothingPiece clothingPiece = clothingPieceRepository.findById(clothingPieceId)
                    .orElseThrow(() -> new IllegalArgumentException("Clothing piece not found"));

            // Set image URL and save it
            clothingPiece.setClothingPieceImgUrl(imageUrl);
            clothingPieceRepository.save(clothingPiece);

            return ResponseEntity.ok("File uploaded successfully. Image URL: " + imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/retrieve/{fileId}")
    public ResponseEntity<FileResponse> retrieveFile(@PathVariable String fileId) {
        try {
            FileResponse fileResponse = fileStorageService.retrieveFile(fileId);
            return ResponseEntity.ok(fileResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}