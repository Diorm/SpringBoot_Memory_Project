package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Filiere;
import com.example.demo.model.Niveau;
import com.example.demo.service.FiliereService;
import com.example.demo.service.NiveauService;

import java.util.List;

@RestController
@RequestMapping("/niveaux")
public class NiveauController {

    @Autowired
    private NiveauService niveauService;

    @Autowired
    private FiliereService filiereService;

    @GetMapping
    public List<Niveau> getAllNiveaux() {
        return niveauService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Niveau> getNiveauById(@PathVariable Long id) {
        Niveau niveau = niveauService.findById(id);
        return niveau != null ? ResponseEntity.ok(niveau) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Niveau createNiveau(@RequestBody Niveau niveau) {
        return niveauService.saveNiveau(niveau);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Niveau> updateNiveau(@PathVariable Long id, @RequestBody Niveau niveauDetails) {
        Niveau niveau = niveauService.findById(id);
        if (niveau == null) {
            return ResponseEntity.notFound().build();
        }
        niveau.setNomNiveau(niveauDetails.getNomNiveau());
        return ResponseEntity.ok(niveauService.saveNiveau(niveau));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNiveau(@PathVariable Long id) {
        niveauService.deleteNiveau(id);
        return ResponseEntity.noContent().build();
    }
    // Get niveaux by filiere ID
    @GetMapping("/filiere/{filiereId}")
    public List<Niveau> getNiveauxByFiliere(@PathVariable Long filiereId) {
        return niveauService.findByFiliereId(filiereId);
    }


    @PostMapping("/filiere/{filiereId}")
    public ResponseEntity<?> addNiveauToFiliere(@PathVariable Long filiereId, @RequestBody Niveau niveauDetails) {
    // Vérifier si la filière existe
    Filiere filiere = filiereService.findById(filiereId);
    if (filiere == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filière non trouvée");
    }

    // Vérifier si le niveau existe déjà dans cette filière
    boolean niveauExiste = niveauService.existsByNomNiveauAndFiliere(niveauDetails.getNomNiveau(), filiere);
    if (niveauExiste) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce niveau existe déjà dans cette filière");
    }

    // Créer le niveau
    niveauDetails.setFiliere(filiere);
    Niveau savedNiveau = niveauService.saveNiveau(niveauDetails);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedNiveau);
   }


@GetMapping("/filiere/{filiereId}/niveau-exist")
public ResponseEntity<Boolean> checkNiveauExist(@PathVariable Long filiereId, @RequestParam String nomNiveau) {
    Filiere filiere = filiereService.findById(filiereId);
    if (filiere == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    
    boolean exists = niveauService.existsByNomNiveauAndFiliere(nomNiveau, filiere);
    return ResponseEntity.ok(exists);
}

}