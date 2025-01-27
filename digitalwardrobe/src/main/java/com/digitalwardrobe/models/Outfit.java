package com.digitalwardrobe.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "outfits")
@NoArgsConstructor
@AllArgsConstructor
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"outfits", "clothingPieces", "calendarEntries"})
    private User user;

    @ManyToMany
    @JoinTable(
        name = "outfit_clothing_piece",
        joinColumns = @JoinColumn(name = "outfit_id"),
        inverseJoinColumns = @JoinColumn(name = "clothing_piece_id")
    )
    @JsonIgnore
    private Set<ClothingPiece> clothingPieces;

    @Transient
    @JsonProperty("clothingPieceIds")
    private Set<Long> clothingPieceIds;

    /* GETTERS */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public Set<ClothingPiece> getClothingPieces() {
        return clothingPieces;
    }

    public Set<Long> getClothingPieceIds() {
        return clothingPieceIds;
    }

    /* SETTERS */

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClothingPieces(Set<ClothingPiece> clothingPieces) {
        this.clothingPieces = clothingPieces;
    }

    public void setClothingPieceIds(Set<Long> clothingPieceIds) {
        this.clothingPieceIds = clothingPieceIds;
    }
    
}
