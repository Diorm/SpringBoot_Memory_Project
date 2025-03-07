package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "module")
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomModule;

    @ManyToOne
    @JoinColumn(name = "ue_id", nullable = false)
    private UE ue;

    // Constructeurs
    public CourseModule() {}

    public CourseModule(String nomModule, UE ue) {
        this.nomModule = nomModule;
        this.ue = ue;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomModule() { return nomModule; }
    public void setNomModule(String nomModule) { this.nomModule = nomModule; }

    public UE getUe() { return ue; }
    public void setUe(UE ue) { this.ue = ue; }
}
