package com.digitalwardrobe.models;

import java.time.LocalDate;

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

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "outfit_id")
    private Outfit outfit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CalendarEntry(LocalDate date, Outfit outfit, User user) {
        this.date = date;
        this.outfit = outfit;
        this.user = user;
    }   

    /* GETTERS */

    public LocalDate getDate() {
        return date;
    }
    
    public Outfit getOutfit() {
        return outfit;
    }

    public User getUser(){
        return user;
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

    @Override
    public String toString() {
        return "CalendarEntry{" +
                "date=" + date +
                ", outfit='" + outfit + '\'' +
                '}';
    }
}
