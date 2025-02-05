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
            CalendarEntryRepository calendarEntryRepository, 
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clothingPieceRepository = clothingPieceRepository;
        this.outfitRepository = outfitRepository;
        this.calendarEntryRepository = calendarEntryRepository;
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
        ClothingPiece cp1 = new ClothingPiece(null, "Zara Baggy black tee", Category.TSHIRTS, Color.BLACK, "https://firebasestorage.googleapis.com/v0/b/digital-wardrobe-4784a.firebasestorage.app/o/8145a181-a23d-4d9c-b282-86148b3dfd74_baggy-black-tee.png?alt=media", user1, null);
        ClothingPiece cp2 = new ClothingPiece(null, "Homeboy jeans", Category.PANTS, Color.BLUE, "https://firebasestorage.googleapis.com/v0/b/digital-wardrobe-4784a.firebasestorage.app/o/90cc94a5-f127-46f9-91d4-a7707097dd25_blue-jean.png?alt=media", user1, null);
        ClothingPiece cp3 = new ClothingPiece(null, "Jordan 4s", Category.SHOES, Color.BLACK, "https://firebasestorage.googleapis.com/v0/b/digital-wardrobe-4784a.firebasestorage.app/o/b12fcde1-b000-4477-8abe-7f6c0e3789bf_jordan-4s.png?alt=media", user1, null);
        ClothingPiece cp4 = new ClothingPiece(null, "Nike Shorts", Category.SHORTS, Color.BLACK, "https://firebasestorage.googleapis.com/v0/b/digital-wardrobe-4784a.firebasestorage.app/o/886ee14d-70d9-4061-9edb-b30de3190ebb_nike-short.png?alt=media", user1, null);
        ClothingPiece cp5 = new ClothingPiece(null, "Converse Cap", Category.HATS, Color.BLUE, "https://firebasestorage.googleapis.com/v0/b/digital-wardrobe-4784a.firebasestorage.app/o/converse-cap.png?alt=media&token=27b96df7-b1ae-4f87-9f67-2b9eecc8f53d", user1, null);
        ClothingPiece cp6 = new ClothingPiece(null, "Pull&Bear Sweatshirt", Category.SWEATSHIRTS, Color.BLACK, "https://firebasestorage.googleapis.com/v0/b/digital-wardrobe-4784a.firebasestorage.app/o/b073cb8a-86bd-4e61-8ada-690945e81500_black-sweatshirt.png?alt=media", user1, null);
        clothingPieceRepository.saveAll(List.of(cp1, cp2, cp3, cp4, cp5, cp6));

        // Create Clothing Pieces for User 2
        ClothingPiece cp7 = new ClothingPiece(null, "Green Hoodie", Category.HOODIES, Color.GREEN, "https://example.com/green-hoodie.jpg", user2, null);
        ClothingPiece cp8 = new ClothingPiece(null, "Gray Joggers", Category.JOGGERS, Color.GRAY, "https://example.com/gray-joggers.jpg", user2, null);
        ClothingPiece cp9 = new ClothingPiece(null, "White Sneakers", Category.SHOES, Color.WHITE, "https://example.com/white-sneakers.jpg", user2, null);
        ClothingPiece cp10 = new ClothingPiece(null, "Black Cap", Category.HATS, Color.BLACK, "https://example.com/black-cap.jpg", user2, null);
        clothingPieceRepository.saveAll(List.of(cp7, cp8, cp9, cp10));

        // Create Outfits for User 1
        Outfit outfit1 = new Outfit(null, "Casual Look", user1, Set.of(cp1, cp2, cp3), Set.of(cp1.getId(), cp2.getId(), cp3.getId()));
        Outfit outfit2 = new Outfit(null, "Sporty Look", user1, Set.of(cp1, cp4, cp3), Set.of(cp1.getId(), cp4.getId(), cp3.getId()));
        outfitRepository.saveAll(List.of(outfit1, outfit2));

        // Create Outfits for User 2
        Outfit outfit3 = new Outfit(null, "Chill Day", user2, Set.of(cp5, cp6, cp7), Set.of(cp5.getId(), cp6.getId(), cp7.getId()));
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