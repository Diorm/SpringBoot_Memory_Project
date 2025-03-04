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

import com.example.demo.model.Semestre;
import com.example.demo.model.UE;
import com.example.demo.service.SemestreService;
import com.example.demo.service.UEService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/ues")
public class UEController {

    @Autowired
    private UEService ueService;

    @Autowired
    private SemestreService semestreService;

    @GetMapping
    public List<UE> getAllUEs() {
        return ueService.findAll();
    }



    @GetMapping("/{id}")
    public ResponseEntity<UE> getUEById(@PathVariable Long id) {
        UE ue = ueService.findById(id);
        return ue != null ? ResponseEntity.ok(ue) : ResponseEntity.notFound().build();
    }



    @PostMapping("/{id}")
    public UE createUE(@RequestBody UE ue) {
        return ueService.saveUE(ue);
    }




    @PutMapping("/{id}")
    public ResponseEntity<UE> updateUE(@PathVariable Long id, @RequestBody UE ueDetails) {
        UE ue = ueService.findById(id);
        if (ue == null) {
            return ResponseEntity.notFound().build();
        }
        ue.setNomUE(ueDetails.getNomUE());
        return ResponseEntity.ok(ueService.saveUE(ue));
    }
  

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUE(@PathVariable Long id) {
        ueService.deleteUE(id);
        return ResponseEntity.noContent().build();
    }   


    //GET /ues/semestre/{id}
    @GetMapping("/semestre/{semestreId}")
    public List<UE> getUEBySemestre(@PathVariable Long semestreId) {
        return ueService.getUE(semestreId);
    }


    @PostMapping("/semestre/{semestreId}")
    public ResponseEntity<?> addUeToSemestre(@PathVariable Long semestreId, @RequestBody UE ueDetail) {
      //verifier si le semestre existe
      Semestre semestre = semestreService.findById(semestreId);
      if (semestre == null) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Semestre non trouvé");
        
      }

      //verifier si l'ue existe
      boolean ueExists = ueService.existsByNomUEAndSemestreId(ueDetail.getNomUE(), semestre);
        if (ueExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("UE existe déjà");
        }

        //creer l'ue
        ueDetail.setSemestre(semestre);
        UE savedUE = ueService.saveUE(ueDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUE);
    }


    @GetMapping("/semestre/{semestreId}/semestre-exist")
   public ResponseEntity<Boolean>checkUeExist(@RequestParam Long semestreId, @PathVariable String nomSemestre){
       Semestre semestre = semestreService.findById(semestreId);
       if (semestre == null) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
       }
       boolean ueExists = ueService.existsByNomUEAndSemestreId(nomSemestre, semestre);
       return ResponseEntity.ok(ueExists);
   }
   
}