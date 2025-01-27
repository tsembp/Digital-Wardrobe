package com.digitalwardrobe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.digitalwardrobe.models.CalendarEntry;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CalendarEntryRepository extends JpaRepository<CalendarEntry, Long> {
    List<CalendarEntry> findByUserUsername(String username);
    List<CalendarEntry> findByUserUsernameAndDateBetween(String username, LocalDateTime start, LocalDateTime end);
}