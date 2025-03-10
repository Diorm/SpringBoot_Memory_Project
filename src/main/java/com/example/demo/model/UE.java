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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@Entity
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomUE;
    private String codeUE;
    private int nbreCredit;

    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    @JsonIgnore
    private Semestre semestre;

    @ManyToOne
    @JoinColumn(name = "niveau_id", nullable = false)
    @JsonIgnore
    @JsonProperty("niveau")
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    @JsonIgnore
    private Filiere filiere;

    @OneToMany(mappedBy = "ue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CourseModule> modules = new ArrayList<>();


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dateAjout;

    // Constructeurs
    public UE() {}

    public UE(String nomUE, String codeUE, int nbreCredit, Semestre semestre, Niveau niveau, Filiere filiere) {
        this.nomUE = nomUE;
        this.codeUE = codeUE;
        this.nbreCredit = nbreCredit;
        this.semestre = semestre;
        this.niveau = niveau;
        this.filiere = filiere;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUE() {
        return nomUE;
    }

    public void setNomUE(String nomUE) {
        this.nomUE = nomUE;
    }

    public String getCodeUE() {
        return codeUE;
    }

    public void setCodeUE(String codeUE) {
        this.codeUE = codeUE;
    }

    public int getNbreCredit() {
        return nbreCredit;
    }

    public void setNbreCredit(int nbreCredit) {
        this.nbreCredit = nbreCredit;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public List<CourseModule> getModules() {
        return modules;
    }

    public void setModules(List<CourseModule> modules) {
        this.modules = modules;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }

    // Méthode pour ajouter un module à l'UE
    public void addModule(CourseModule module) {
        modules.add(module);
        module.setUe(this);
    }

    // Méthode pour supprimer un module de l'UE
    public void removeModule(CourseModule module) {
        modules.remove(module);
        module.setUe(null);
    }

    // Méthode toString pour le débogage
    @Override
    public String toString() {
        return "UE{" +
                "id=" + id +
                ", nomUE='" + nomUE + '\'' +
                ", codeUE='" + codeUE + '\'' +
                ", nbreCredit=" + nbreCredit +
                ", semestre=" + semestre +
                ", niveau=" + niveau +
                ", filiere=" + filiere +
                ", dateAjout=" + dateAjout +
                '}';
    }
}