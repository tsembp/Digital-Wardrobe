package com.digitalwardrobe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalwardrobe.models.ClothingPiece;
import com.digitalwardrobe.models.User;

@Repository
public interface ClothingPieceRepository extends JpaRepository<ClothingPiece, Long> {
    List<ClothingPiece> findByUser(User user);
    Optional<ClothingPiece> findByIdAndUserUsername(Long id, String username);
}

