package com.example.demo.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Filiere;
import com.example.demo.model.Niveau;
import com.example.demo.model.Semestre;
import com.example.demo.model.UE;
import com.example.demo.service.FiliereService;
import com.example.demo.service.NiveauService;
import com.example.demo.service.SemestreService;
import com.example.demo.service.UEService;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/ues")
public class UEController {
    @Autowired
    private UEService ueService;

    @Autowired
    public SemestreService semestreService;

    @Autowired
    public NiveauService niveauService;

    @Autowired
    private FiliereService filiereService;

    @GetMapping
    public List<UE> getAllUEs() {
        return ueService.getAllUEs();
    }

    @GetMapping("/{id}")
    public UE getUEById(@PathVariable Long id) {
        return ueService.getUEById(id);
    }

    // @PostMapping
    // public UE createUE(@RequestBody UE ue) {
    //     return ueService.saveUE(ue);
    // }

    @DeleteMapping("/{id}")
    public void deleteUE(@PathVariable Long id) {
        ueService.deleteUE(id);
    }

    // @GetMapping("/{id}/modules")
    // public List<CourseModule> getModulesByUE(@PathVariable Long id) {
    //     return ueService.getModulesByUE(id);
    // }


    //UE pour chaque semestre
    @GetMapping("semestre/{semestreId}")
    public List<UE> getUEBySemestre(@PathVariable Long semestreId){
        return ueService.getUEBySemestre(semestreId);
    }
   
    

    //Ajouter une UE a un Semestre

    @PostMapping("/{semestreId}/ue")
    public ResponseEntity<?> addUEToSemestre(@PathVariable Long semestreId, @RequestBody UE ueDetail) {
        // Vérifier l'existence du semestre
        Semestre semestre = semestreService.getSemestreById(semestreId);
        if (semestre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Semestre Non Trouvé");
        }
    
        // Vérifier l'existence du niveau
        if (ueDetail.getNiveau() == null || ueDetail.getNiveau().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Niveau manquant ou invalide");
        }
        Niveau niveau = niveauService.getNiveauById(ueDetail.getNiveau().getId());
        if (niveau == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Niveau non Trouvé");
        }
    
        // Vérifier l'existence de la filière
        if (ueDetail.getFiliere() == null || ueDetail.getFiliere().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filière manquante ou invalide");
        }
        Filiere filiere = filiereService.getFiliereById(ueDetail.getFiliere().getId());
        if (filiere == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filiere non Trouvé");
        }
    
        // Associer le semestre, le niveau et la filière à l'UE
        ueDetail.setSemestre(semestre);
        ueDetail.setNiveau(niveau);
        ueDetail.setFiliere(filiere);
    
        // Enregistrer l'UE
        UE savedUe = ueService.saveUE(ueDetail);
    
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUe);
    }







    ///Existence de l'UE
    @GetMapping("exists")
   public ResponseEntity<Boolean> checkUEExist(@RequestParam String nomUE,@RequestParam Long semestreId){
        boolean exists=ueService.ueExist(nomUE, semestreId);

        return ResponseEntity.ok(exists);
   }
    
}
