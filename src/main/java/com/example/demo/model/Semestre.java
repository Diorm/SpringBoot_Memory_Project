package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomSemestre;

    @ManyToOne
    @JoinColumn(name = "niveau_id", nullable = false)
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere; // Ajout de la relation avec la filière

    @OneToMany(mappedBy = "semestre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UE> ues = new ArrayList<>();

    // Constructeurs
    public Semestre() {}

    public Semestre(String nomSemestre, Niveau niveau, Filiere filiere) {
        this.nomSemestre = nomSemestre;
        this.niveau = niveau;
        this.filiere = filiere;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomSemestre() { return nomSemestre; }
    public void setNomSemestre(String nomSemestre) { this.nomSemestre = nomSemestre; }

    public Niveau getNiveau() { return niveau; }
    public void setNiveau(Niveau niveau) { this.niveau = niveau; }

    public Filiere getFiliere() { return filiere; } // Getter pour Filiere
    public void setFiliere(Filiere filiere) { this.filiere = filiere; } // Setter pour Filiere

    public List<UE> getUes() { return ues; }
    public void setUes(List<UE> ues) { this.ues = ues; }
}
