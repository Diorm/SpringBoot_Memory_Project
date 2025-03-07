package com.example.demo.model;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomFiliere;

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Niveau> niveaux = new ArrayList<>();

    // Constructeurs
    public Filiere() {}

    public Filiere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomFiliere() { return nomFiliere; }
    public void setNomFiliere(String nomFiliere) { this.nomFiliere = nomFiliere; }

    public List<Niveau> getNiveaux() { return niveaux; }
    public void setNiveaux(List<Niveau> niveaux) { this.niveaux = niveaux; }

    @Override
    public String toString() {
        return "Filiere{id=" + id + ", nomFiliere='" + nomFiliere + "'}";
    }

}
