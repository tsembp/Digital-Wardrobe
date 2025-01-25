package com.digitalwardrobe.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Admin")
@NoArgsConstructor
public class Admin extends User {

   
    
}
