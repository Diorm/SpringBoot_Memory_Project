package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Niveau;
import com.example.demo.model.Semestre;
import com.example.demo.service.NiveauService;
import com.example.demo.service.SemestreService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/semestres")
public class SemestreController {

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private NiveauService niveauService;

    @GetMapping
    public List<Semestre> getAllSemestres() {
        return semestreService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semestre> getSemestreById(@PathVariable Long id) {
        Semestre semestre = semestreService.findById(id);
        return semestre != null ? ResponseEntity.ok(semestre) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Semestre createSemestre(@RequestBody Semestre semestre) {
        return semestreService.saveSemestre(semestre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semestre> updateSemestre(@PathVariable Long id, @RequestBody Semestre semestreDetails) {
        Semestre semestre = semestreService.findById(id);
        if (semestre == null) {
            return ResponseEntity.notFound().build();
        }
        semestre.setNomSemestre(semestreDetails.getNomSemestre());
        return ResponseEntity.ok(semestreService.saveSemestre(semestre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemestre(@PathVariable Long id) {
        semestreService.deleteSemestre(id);
        return ResponseEntity.noContent().build();
    }



    // Get semestres by niveau ID
    @GetMapping("/niveau/{niveauId}")
    public List<Semestre> getSemestresByNiveau(@PathVariable Long niveauId) {
        return semestreService.findByNiveauId(niveauId);
    }


    @PostMapping("/niveau/{niveauId}")
    public ResponseEntity<?> addSemestreToNiveau(@PathVariable Long niveauId, @RequestBody Semestre semestreDeatail) {
        // Vérifier si le niveau existe
        Niveau niveau = niveauService.findById(niveauId);
        if (niveau == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Niveau non trouvé");
        }

        // Vérifier si le semestre existe
        boolean semestreExiste = semestreService.existsByNomSemestreAndNiveauId(semestreDeatail.getNomSemestre(), niveau);
        if (semestreExiste) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Semestre déjà existant");
        }
        // Créer le semestre
        semestreDeatail.setNiveau(niveau);
       Semestre savedSemestre = semestreService.saveSemestre(semestreDeatail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSemestre);
   
    
    }


    @GetMapping("/niveau/{niveauId}/semestre-exist")
    public ResponseEntity<Boolean> checkSemestreExist(@PathVariable Long niveauId, @RequestParam String nomSemestre) {
        Niveau niveau = niveauService.findById(niveauId);
        if (niveau == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        boolean exists= semestreService.existsByNomSemestreAndNiveauId(nomSemestre, niveau);
        return ResponseEntity.ok(exists);
    }
       
      
    
}