package com.digitalwardrobe.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "calendar-entries")
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "outfit_id")
    @JsonIgnoreProperties({"user", "clothingPieces"})
    private Outfit outfit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"outfits", "clothingPieces", "calendarEntries"})
    private User user;

    private String occasion;
    private String notes;

    public CalendarEntry(LocalDate date, Outfit outfit, User user, String occasion, String notes) {
        this.date = date;
        this.outfit = outfit;
        this.user = user;
        this.occasion = occasion;
        this.notes = notes;
    }   

    /* GETTERS */

    public Long getId(){
        return id;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public Outfit getOutfit() {
        return outfit;
    }

    public User getUser(){
        return user;
    }

    public String getOccasion(){
        return occasion;
    }

    public String getNotes(){
        return notes;
    }

    /* SETTERS */

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setOutfit(Outfit outfit) {
        this.outfit = outfit;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setOccasion(String occasion){
        this.occasion = occasion;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CalendarEntry{" +
                "date=" + date +
                ", outfit='" + outfit + '\'' +
                '}';
    }
}
