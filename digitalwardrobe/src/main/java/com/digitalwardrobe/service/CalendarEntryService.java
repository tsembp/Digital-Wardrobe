package com.digitalwardrobe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digitalwardrobe.models.CalendarEntry;
import com.digitalwardrobe.repository.CalendarEntryRepository;
import com.digitalwardrobe.repository.UserRepository;
import com.digitalwardrobe.dto.CalendarEntryDTO;
import com.digitalwardrobe.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CalendarEntryService {
    
    @Autowired
    private CalendarEntryRepository calendarEntryRepository;
    
    @Autowired
    private UserRepository userRepository;

    public CalendarEntry createEntry(CalendarEntry entry, String username) {
        entry.setUser(userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return calendarEntryRepository.save(entry);
    }

    public CalendarEntry updateEntry(Long id, CalendarEntry entry, String username) {
        CalendarEntry existingEntry = calendarEntryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Calendar entry not found"));
        
        if (!existingEntry.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Not authorized to update this entry");
        }
        
        existingEntry.setDate(entry.getDate());
        existingEntry.setOutfit(entry.getOutfit());
        existingEntry.setOccasion(entry.getOccasion());
        existingEntry.setNotes(entry.getNotes());
        
        return calendarEntryRepository.save(existingEntry);
    }

    public void deleteEntry(Long id, String username) {
        CalendarEntry entry = calendarEntryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Calendar entry not found"));
            
        if (!entry.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Not authorized to delete this entry");
        }
        
        calendarEntryRepository.delete(entry);
    }

    public List<CalendarEntryDTO> getUserEntries(String username) {
        return calendarEntryRepository.findByUserUsername(username)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<CalendarEntry> getEntriesForDateRange(String username, LocalDateTime start, LocalDateTime end) {
        return calendarEntryRepository.findByUserUsernameAndDateBetween(username, start, end);
    }
    
    private CalendarEntryDTO convertToDTO(CalendarEntry entry) {
        return new CalendarEntryDTO(
            entry.getId(),
            entry.getDate(),
            entry.getOccasion(),
            entry.getNotes(),
            entry.getOutfit().getId(),
            entry.getOutfit().getName(),
            entry.getUser().getUsername()
        );
    }
}