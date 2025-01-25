package com.digitalwardrobe.seeder;

import com.digitalwardrobe.models.*;
import com.digitalwardrobe.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ClothingPieceRepository clothingPieceRepository;
    private final OutfitRepository outfitRepository;
    private final CalendarEntryRepository calendarEntryRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(
            UserRepository userRepository, 
            ClothingPieceRepository clothingPieceRepository,
            OutfitRepository outfitRepository,
            CalendarEntryRepository calendarEntryRepository, // Add this
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clothingPieceRepository = clothingPieceRepository;
        this.outfitRepository = outfitRepository;
        this.calendarEntryRepository = calendarEntryRepository; // Add this
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Create Users
        User user1 = new User(
            null, 
            "John", 
            "Doe", 
            "john.doe@example.com", 
            "johndoe", 
            passwordEncoder.encode("password"), 
            Role.USER
        );

        User user2 = new User(
            null, 
            "Alice", 
            "Smith", 
            "alice.smith@example.com", 
            "asmith", 
            passwordEncoder.encode("password"), 
            Role.USER
        );
        userRepository.saveAll(List.of(user1, user2));

        // Create Clothing Pieces for User 1
        ClothingPiece cp1 = new ClothingPiece(null, "Red T-Shirt", Category.TSHIRTS, "https://example.com/red-tshirt.jpg", user1, null);
        ClothingPiece cp2 = new ClothingPiece(null, "Blue Jeans", Category.PANTS, "https://example.com/blue-jeans.jpg", user1, null);
        ClothingPiece cp3 = new ClothingPiece(null, "Black Sneakers", Category.SHOES, "https://example.com/black-sneakers.jpg", user1, null);
        ClothingPiece cp4 = new ClothingPiece(null, "Nike Shorts", Category.SHORTS, "https://example.com/nike-shorts.jpg", user1, null);
        clothingPieceRepository.saveAll(List.of(cp1, cp2, cp3, cp4));

        // Create Clothing Pieces for User 2
        ClothingPiece cp5 = new ClothingPiece(null, "Green Hoodie", Category.HOODIES, "https://example.com/green-hoodie.jpg", user2, null);
        ClothingPiece cp6 = new ClothingPiece(null, "Gray Joggers", Category.JOGGERS, "https://example.com/gray-joggers.jpg", user2, null);
        ClothingPiece cp7 = new ClothingPiece(null, "White Sneakers", Category.SHOES, "https://example.com/white-sneakers.jpg", user2, null);
        ClothingPiece cp8 = new ClothingPiece(null, "Black Cap", Category.HATS, "https://example.com/black-cap.jpg", user2, null);
        clothingPieceRepository.saveAll(List.of(cp5, cp6, cp7, cp8));

        // Create Outfits for User 1
        Outfit outfit1 = new Outfit(null, "Casual Look", user1, Set.of(cp1, cp2, cp3));
        Outfit outfit2 = new Outfit(null, "Sporty Look", user1, Set.of(cp1, cp4, cp3));
        outfitRepository.saveAll(List.of(outfit1, outfit2));

        // Create Outfits for User 2
        Outfit outfit3 = new Outfit(null, "Chill Day", user2, Set.of(cp5, cp6, cp7));
        outfitRepository.save(outfit3);

        CalendarEntry entry1 = new CalendarEntry(
            null,
            LocalDate.now().plusDays(1),
            outfit1, // Casual Look for user1
            user1,
            "Dinner with friends",
            "Restaurant dress code is smart casual"
        );

        CalendarEntry entry2 = new CalendarEntry(
            null,
            LocalDate.now().plusDays(3),
            outfit2, // Sporty Look for user1
            user1,
            "Gym session",
            "Remember to pack water bottle"
        );

        CalendarEntry entry3 = new CalendarEntry(
            null,
            LocalDate.now().plusDays(2),
            outfit3, // Chill Day for user2
            user2,
            "Movie night",
            "Going to see the new Marvel movie"
        );

        calendarEntryRepository.saveAll(List.of(entry1, entry2, entry3));

        System.out.println("\u001B[32mDatabase seeded successfully!\u001B[0m");
    }
}