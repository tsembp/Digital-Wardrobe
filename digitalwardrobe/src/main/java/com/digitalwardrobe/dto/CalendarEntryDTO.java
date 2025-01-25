package com.digitalwardrobe.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEntryDTO {
    private Long id;
    private LocalDate date;
    private String occasion;
    private String notes;
    private Long outfitId;
    private String outfitName;
    private String username;
}