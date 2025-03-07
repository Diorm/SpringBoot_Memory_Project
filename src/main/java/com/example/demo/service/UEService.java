package com.example.demo.service;


import java.util.List;

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

    public UE getUEById(Long id) {
        return ueRepository.findById(id).orElseThrow(() -> new RuntimeException("UE non trouvée"));
    }

    public UE saveUE(UE ue) {
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
