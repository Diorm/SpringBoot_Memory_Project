package com.example.demo.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table(name = "sessions") // Nom de la table dans la base de données
public class Session {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomSession;
  
}