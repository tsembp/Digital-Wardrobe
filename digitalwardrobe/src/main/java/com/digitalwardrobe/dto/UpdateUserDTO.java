package com.digitalwardrobe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}