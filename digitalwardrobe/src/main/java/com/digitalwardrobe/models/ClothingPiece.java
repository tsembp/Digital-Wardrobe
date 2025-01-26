package com.digitalwardrobe.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clothes")
@NoArgsConstructor
@AllArgsConstructor
public class ClothingPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"clothingPieces", "outfits", "calendarEntries"})
    private User user;

    @ManyToMany(mappedBy = "clothingPieces")
    @JsonIgnoreProperties("clothingPieces")
    private Set<Outfit> outfits;

    /* GETTERS */
    public String getClothingPieceName(){
        return name;
    }

    public Category getClothingPieceCategory(){
        return category;
    }

    public String getClothingPieceImgUrl(){
        return imgUrl;
    }

    /* SETTERS */
    public void setClothingPieceName(String value){
        this.name = value;
    }

    public void setClothingPieceCategory(Category value){
        this.category = value;
    }

    public void setClothingPieceImgUrl(String value){
        this.imgUrl = value;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Object getName() {
        return this.name;
    }

    public Object getCategory() {
        return this.category;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}
