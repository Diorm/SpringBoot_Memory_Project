package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Filiere;
import com.example.demo.model.Niveau;
import com.example.demo.service.FiliereService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/filieres")
public class FiliereController {
    @Autowired
    private FiliereService filiereService;
  
    @GetMapping("/{id}/niveaux")
    public List<Niveau> getNiveauxByFiliere(@PathVariable Long id) {
        return filiereService.getNiveauxByFiliere(id);
    }

    @GetMapping
    public List<Filiere> getAllFilieres() {
        return filiereService.getAllFilieres();
    }

    @GetMapping("/{id}")
    public Filiere getFiliereById(@PathVariable Long id) {
        return filiereService.getFiliereById(id);
    }

    @PostMapping
    public Filiere createFiliere(@RequestBody Filiere filiere) {
        return filiereService.saveFiliere(filiere);
    }

    @DeleteMapping("/{id}")
    public void deleteFiliere(@PathVariable Long id) {
        filiereService.deleteFiliere(id);
    }
}
