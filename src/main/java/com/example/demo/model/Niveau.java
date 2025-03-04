package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = {"filiere", "semestres"})
@EqualsAndHashCode(exclude = {"filiere", "semestres"})
@Table(name = "niveaux")
public class Niveau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomNiveau;

    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    @JsonBackReference
    private Filiere filiere;

    @JsonManagedReference
    @OneToMany(mappedBy = "niveau", cascade = CascadeType.ALL)
    private List<Semestre> semestres;
}


