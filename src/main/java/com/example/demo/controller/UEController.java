package com.example.demo.controller;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.example.demo.model.Filiere;
import com.example.demo.model.Niveau;
import com.example.demo.model.Semestre;
import com.example.demo.model.UE;
import com.example.demo.repository.UeRepository;
import com.example.demo.service.FiliereService;
import com.example.demo.service.NiveauService;
import com.example.demo.service.SemestreService;
import com.example.demo.service.UEService;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/ues")
public class UEController {

    @Autowired
    private UeRepository ueRepository;

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private NiveauService niveauService;

    @Autowired
    private FiliereService filiereService;

    @Autowired
    private UEService ueService;

    // Récupérer toutes les UEs
    @GetMapping
    public ResponseEntity<List<UE>> getAllUEs() {
        List<UE> ues = ueRepository.findAll();
        return new ResponseEntity<>(ues, HttpStatus.OK);
    }

    // Récupérer une UE par son ID
    @GetMapping("/{id}")
    public ResponseEntity<UE> getUEById(@PathVariable("id") Long id) {
        Optional<UE> ueData = ueRepository.findById(id);

        if (ueData.isPresent()) {
            return new ResponseEntity<>(ueData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Créer une nouvelle UE
    @PostMapping
    public ResponseEntity<UE> createUE(@RequestBody UE ue) {
        try {
            ue.setDateAjout(LocalDateTime.now());
            UE _ue = ueRepository.save(ue);
            return new ResponseEntity<>(_ue, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mettre à jour une UE existante
    @PutMapping("/{ueId}")
public ResponseEntity<?> updateUE(@PathVariable Long ueId, @RequestBody Map<String, Object> body) {
    // Affiche les données reçues pour le débogage
    System.out.println("Données reçues pour mise à jour : " + body);

    // Vérifier si l'UE existe
    Optional<UE> existingUEOpt = ueService.getUEById(ueId);
    if (!existingUEOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UE non trouvée");
    }

    // Récupérer l'UE existante
    UE existingUE = existingUEOpt.get();

    // Mettre à jour les propriétés de l'UE
    existingUE.setNomUE((String) body.get("nomUE"));
    existingUE.setCodeUE((String) body.get("codeUE"));
    existingUE.setNbreCredit((Integer) body.get("nbreCredit"));

    // Récupérer les objets Niveau, Filière et Semestre si nécessaire
    if (body.get("niveau") != null) {
        Map<String, Object> niveauObj = (Map<String, Object>) body.get("niveau");
        if (niveauObj.get("id") != null) {
            Niveau niveau = niveauService.getNiveauById(Long.valueOf(niveauObj.get("id").toString()));
            existingUE.setNiveau(niveau);
        }
    }

    if (body.get("filiere") != null) {
        Map<String, Object> filiereObj = (Map<String, Object>) body.get("filiere");
        if (filiereObj.get("id") != null) {
            Filiere filiere = filiereService.getFiliereById(Long.valueOf(filiereObj.get("id").toString()));
            existingUE.setFiliere(filiere);
        }
    }

    if (body.get("semestre") != null) {
        Map<String, Object> semestreObj = (Map<String, Object>) body.get("semestre");
        if (semestreObj.get("id") != null) {
            Semestre semestre = semestreService.getSemestreById(Long.valueOf(semestreObj.get("id").toString()));
            existingUE.setSemestre(semestre);
        }
    }

    // Conversion de la dateAjout en LocalDateTime (si nécessaire)
    String dateAjoutStr = (String) body.get("dateAjout");
    if (dateAjoutStr != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime dateAjout = LocalDateTime.parse(dateAjoutStr, formatter);
        existingUE.setDateAjout(dateAjout);
    }

    // Enregistrer l'UE mise à jour
    UE updatedUE = ueService.saveUE(existingUE);

    // Réponse avec les données de l'UE mise à jour
    return ResponseEntity.ok(updatedUE);
}
    // @PutMapping("/{id}")
    // public ResponseEntity<UE> updateUE(@PathVariable("id") Long id, @RequestBody UE ue) {
    //     Optional<UE> ueData = ueRepository.findById(id);

    //     if (ueData.isPresent()) {
    //         UE _ue = ueData.get();
    //         _ue.setNomUE(ue.getNomUE());
    //         _ue.setCodeUE(ue.getCodeUE());
    //         _ue.setNbreCredit(ue.getNbreCredit());
    //         _ue.setSemestre(ue.getSemestre());
    //         _ue.setNiveau(ue.getNiveau());
    //         _ue.setFiliere(ue.getFiliere());
    //         // Ne pas oublier de gérer la mise à jour des modules si nécessaire
    //         _ue.setDateAjout(LocalDateTime.now()); // Mise à jour de la date de modification
    //         return new ResponseEntity<>(ueRepository.save(_ue), HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    

    // Supprimer toutes les UEs
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllUEs() {
        try {
            ueRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



      //Ajouter une UE a un Semestre
    @PostMapping("/{semestreId}/ue")
    public ResponseEntity<?> addUEToSemestre(@PathVariable Long semestreId, @RequestBody Map<String, Object> body) {
    // Affiche les données reçues pour le débogage
    System.out.println("Données reçues : " + body);

    // Extraction des données du corps de la requête
    Object niveauObj = body.get("niveau");
    Object filiereObj = body.get("filiere");
    Object semestreObj = body.get("semestre");

    // Vérifier si les éléments sont présents et valides
    if (niveauObj == null || ((Map) niveauObj).get("id") == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Niveau manquant ou invalide");
    }
    if (filiereObj == null || ((Map) filiereObj).get("id") == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filière manquante ou invalide");
    }
    if (semestreObj == null || ((Map) semestreObj).get("id") == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Semestre manquant ou invalide");
    }

    // Récupérer les objets Semestre, Niveau et Filiere
    Semestre semestre = semestreService.getSemestreById(semestreId);
    Niveau niveau = niveauService.getNiveauById(Long.valueOf(((Map) niveauObj).get("id").toString()));
    Filiere filiere = filiereService.getFiliereById(Long.valueOf(((Map) filiereObj).get("id").toString()));

    // Créer une nouvelle UE
    UE ue = new UE();
    ue.setSemestre(semestre);
    ue.setNiveau(niveau);
    ue.setFiliere(filiere);

    // Assigner les autres propriétés
    ue.setNomUE((String) body.get("nomUE"));
    ue.setCodeUE((String) body.get("codeUE"));
    ue.setNbreCredit((Integer) body.get("nbreCredit"));

    // Conversion de la dateAjout en LocalDateTime
    String dateAjoutStr = (String) body.get("dateAjout");
    if (dateAjoutStr != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime dateAjout = LocalDateTime.parse(dateAjoutStr, formatter);
        ue.setDateAjout(dateAjout);
    }

    // Enregistrer l'UE
    UE savedUe = ueService.saveUE(ue);

    // Réponse avec les données de l'UE ajoutée
    return ResponseEntity.status(HttpStatus.CREATED).body(savedUe);
}

//     //UE pour chaque semestre
    @GetMapping("semestre/{semestreId}")
    public List<UE> getUEBySemestre(@PathVariable Long semestreId){
        return ueService.getUEBySemestre(semestreId);
    }




    ///Existence de l'UE
    @GetMapping("/exists")
   public ResponseEntity<Boolean> checkUEExist(@RequestParam String nomUE,@RequestParam Long semestreId){
        boolean exists=ueService.ueExist(nomUE, semestreId);

        return ResponseEntity.ok(exists);
   }



    @DeleteMapping("/{id}")
    public void deleteUE(@PathVariable Long id) {
        ueService.deleteUE(id);
    }

    
}


// @RestController
// @RequestMapping("/ues")
// public class UEController {
//     @Autowired
//     private UEService ueService;

//     @Autowired
//     public SemestreService semestreService;

//     @Autowired
//     public NiveauService niveauService;

//     @Autowired
//     private FiliereService filiereService;

//     @GetMapping
//     public List<UE> getAllUEs() {
//         return ueService.getAllUEs();
//     }

//     @GetMapping("/{id}")
//     public UE getUEById(@PathVariable Long id) {
//         return ueService.getUEById(id);
//     }

//     // @PostMapping
//     // public UE createUE(@RequestBody UE ue) {
//     //     return ueService.saveUE(ue);
//     // }

//     // @GetMapping("/{id}/modules")
//     // public List<CourseModule> getModulesByUE(@PathVariable Long id) {
//     //     return ueService.getModulesByUE(id);
//     // }


//     //UE pour chaque semestre
//     @GetMapping("semestre/{semestreId}")
//     public List<UE> getUEBySemestre(@PathVariable Long semestreId){
//         return ueService.getUEBySemestre(semestreId);
//     }
   
    

//     //Ajouter une UE a un Semestre

//     @PostMapping("/{semestreId}/ue")
//     public ResponseEntity<?> addUEToSemestre(@PathVariable Long semestreId, @RequestBody UE ueDetail) {
//         // Vérifier l'existence du semestre
//         Semestre semestre = semestreService.getSemestreById(semestreId);
//         if (semestre == null) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Semestre Non Trouvé");
//         }
    
//         // Vérifier l'existence du niveau
//         if (ueDetail.getNiveau() == null || ueDetail.getNiveau().getId() == null) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Niveau manquant ou invalide");
//         }
//         Niveau niveau = niveauService.getNiveauById(ueDetail.getNiveau().getId());
//         if (niveau == null) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Niveau non Trouvé");
//         }
    
//         // Vérifier l'existence de la filière
//         if (ueDetail.getFiliere() == null || ueDetail.getFiliere().getId() == null) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filière manquante ou invalide");
//         }
//         Filiere filiere = filiereService.getFiliereById(ueDetail.getFiliere().getId());
//         if (filiere == null) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filiere non Trouvé");
//         }
    
//         // Associer le semestre, le niveau et la filière à l'UE
//         ueDetail.setSemestre(semestre);
//         ueDetail.setNiveau(niveau);
//         ueDetail.setFiliere(filiere);
    
//         // Enregistrer l'UE
//         UE savedUe = ueService.saveUE(ueDetail);
    
//         return ResponseEntity.status(HttpStatus.CREATED).body(savedUe);
//     }







// }
