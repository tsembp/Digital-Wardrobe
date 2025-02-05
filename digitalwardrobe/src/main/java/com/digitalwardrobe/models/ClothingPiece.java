package com.digitalwardrobe.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
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

    @Enumerated(EnumType.STRING)
    private Color color;

    @JsonProperty("imgUrl")
    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"clothingPieces", "outfits", "calendarEntries"})
    private User user;

    @ManyToMany(mappedBy = "clothingPieces")
    @JsonIgnoreProperties("clothingPieces")
    private Set<Outfit> outfits;

    /* GETTERS AND SETTERS */
    public Long getId(){
        return id;
    }

    public Object getName() {
        return this.name;
    }

    public Object getCategory() {
        return this.category;
    }

    public String getClothingPieceName(){
        return name;
    }

    public Category getClothingPieceCategory(){
        return category;
    }

    public String getClothingPieceImgUrl(){
        return imgUrl;
    }

    public Set<Outfit> getOutfits() {
        return outfits;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public Color getColor(){
        return color;
    }

    public void setOutfits(Set<Outfit> outfits) {
        this.outfits = outfits;
    }

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

    public void setName(String value) {
        this.name = value;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setColor(Color color){
        this.color = color;
    }
    
}
