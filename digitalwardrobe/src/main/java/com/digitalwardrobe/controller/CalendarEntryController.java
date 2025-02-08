package com.digitalwardrobe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.digitalwardrobe.dto.CalendarEntryDTO;
import com.digitalwardrobe.models.CalendarEntry;
import com.digitalwardrobe.service.CalendarEntryService;

import java.util.List;

@RestController
@RequestMapping("/api/calendarentries")
public class CalendarEntryController {

    @Autowired
    private CalendarEntryService calendarEntryService;

    @PostMapping
    public ResponseEntity<CalendarEntry> createEntry(
            @RequestBody CalendarEntry entry,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(calendarEntryService.createEntry(entry, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarEntry> updateEntry(
            @PathVariable Long id,
            @RequestBody CalendarEntry entry,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(calendarEntryService.updateEntry(id, entry, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        calendarEntryService.deleteEntry(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CalendarEntryDTO>> getUserEntries(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(calendarEntryService.getUserEntries(userDetails.getUsername()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}