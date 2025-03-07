package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Filiere;
import com.example.demo.model.Niveau;
import com.example.demo.model.Semestre;
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
        return niveauService.getAllNiveaux();
    }

    @GetMapping("/{id}")
    public Niveau getNiveauById(@PathVariable Long id) {    
        return niveauService.getNiveauById(id);
    }

    @PostMapping
    public Niveau createNiveau(@RequestBody Niveau niveau) {
        return niveauService.saveNiveau(niveau);
    }

    @DeleteMapping("/{id}")
    public void deleteNiveau(@PathVariable Long id) {
        niveauService.deleteNiveau(id);
    }

      @GetMapping("/{id}/semestres")
    public List<Semestre> getSemestresByNiveau(@PathVariable Long id) {
        return niveauService.getSemestresByNiveau(id);
    }


    //Niveaux pour chaque filiere

    @GetMapping("/filiere/{filiereId}")
    public List<Niveau> getNiveauxByFiliere(@PathVariable Long filiereId) {
        return niveauService.getNiveauxByFiliere(filiereId);
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

      // Vérifier si un niveau existe par ID
    // Vérifier si un niveau existe pour une filière donnée
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkNiveauExists(
            @RequestParam String nomNiveau,
            @RequestParam Long filiereId) {
        boolean exists = niveauService.niveauExists(nomNiveau, filiereId);
        return ResponseEntity.ok(exists);
    }


//Pour recuperer l'id d'une filiere
    @GetMapping("/{niveauId}/filiere")
public ResponseEntity<Filiere> getFiliereByNiveauId(@PathVariable Long niveauId) {
    // Récupérer le niveau
    Niveau niveau = niveauService.getNiveauById(niveauId);
    if (niveau == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Récupérer la filière associée au niveau
    Filiere filiere = niveau.getFiliere();
    if (filiere == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok(filiere);
}





}
