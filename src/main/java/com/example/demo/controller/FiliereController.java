package com.example.demo.controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Filiere;
import com.example.demo.service.FiliereService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/filieres")
public class FiliereController {


    @Autowired
    private FiliereService filiereService;

    @PostMapping
    public ResponseEntity<Filiere> createFiliere(@RequestBody Filiere filiere) {
        if (filiere.getNomFiliere()==null || filiere.getNomFiliere().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Filiere createdFiliere=filiereService.creatFiliere(filiere);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFiliere);
    }
    

    // Récupérer une filière par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Filiere> getFiliere(@PathVariable Long id) {
        Optional<Filiere> filiere = filiereService.getFiliere(id);
        return filiere.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Iterable<Filiere> getFiliere() {
        return filiereService.getFiliere();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Filiere> updateFiliere(@PathVariable Long id, @RequestBody Filiere filiere) {
        if(filiereService.getFiliere(id).isPresent()){
            filiere.setId(id);
            return ResponseEntity.ok(filiereService.creatFiliere(filiere));
        }
        return ResponseEntity.notFound().build();
        
     
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliere(@PathVariable Long id){
        filiereService.deleteFiliere(id);
        return ResponseEntity.noContent().build();
    }

}
