package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = {"semestre", "modules"})
@EqualsAndHashCode(exclude = {"semestre", "modules"})
@Table(name = "ues")
public class UE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomUE;

    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    @JsonBackReference
    private Semestre semestre;

    @JsonManagedReference
    @OneToMany(mappedBy = "ue", cascade = CascadeType.ALL)
    private List<Module> modules;
}