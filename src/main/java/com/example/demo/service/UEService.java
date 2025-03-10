package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CourseModule;
import com.example.demo.model.UE;
import com.example.demo.repository.UeRepository;


@Service
public class UEService {
    @Autowired
    private UeRepository ueRepository;

    public List<UE> getAllUEs() {
        return ueRepository.findAll();
    }

    // public UE getUEById(Long id) {
    //     return ueRepository.findById(id).orElseThrow(() -> new RuntimeException("UE non trouvée"));
    // }

    public Optional<UE> getUEById(Long ueId) {
        return ueRepository.findById(ueId); // Assurez-vous que le repository retourne Optional
    }
    // public UE saveUE(UE ue) {
    //     return ueRepository.save(ue);
    // }
    public UE saveUE(UE ue) {
        // Vérifie si l'UE a bien été associée à une filière, niveau et semestre
        if (ue.getFiliere() == null || ue.getNiveau() == null || ue.getSemestre() == null) {
            throw new IllegalArgumentException("Filière, Niveau ou Semestre manquant");
        }
        return ueRepository.save(ue);
    }

    public void deleteUE(Long id) {
        ueRepository.deleteById(id);
    }

    public List<CourseModule> getModulesByUE(Long ueId) {
        UE ue = ueRepository.findById(ueId)
                .orElseThrow(() -> new RuntimeException("UE non trouvée"));
        return ue.getModules();
    }

    //recuperer les UES de chaque Semestre

    public List<UE> getUEBySemestre(Long semestreId){
        return ueRepository.findBySemestreId(semestreId);
    }

    ///Verification de l'existence d'une UE
    public boolean ueExist(String nomUE,long semestreId){
        return ueRepository.existsByNomUEAndSemestreId(nomUE,semestreId);
    }
}
