package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Semestre;
import com.example.demo.model.UE;
import com.example.demo.repository.SemestreRepository;



@Service
public class SemestreService {
    @Autowired
    private SemestreRepository semestreRepository;

    public List<Semestre> getAllSemestres() {
        return semestreRepository.findAll();
    }

    public Semestre getSemestreById(Long id) {
        return semestreRepository.findById(id).orElseThrow(() -> new RuntimeException("Semestre non trouvé"));
    }

    public Semestre saveSemestre(Semestre semestre) {
        return semestreRepository.save(semestre);
    }

    public void deleteSemestre(Long id) {
        semestreRepository.deleteById(id);
    }

      public List<UE> getUEsBySemestre(Long semestreId) {
        Semestre semestre = semestreRepository.findById(semestreId)
                .orElseThrow(() -> new RuntimeException("Semestre non trouvé"));
        return semestre.getUes();
    }



    //Recuperer les semestres de chaque niveau

    public List<Semestre>getSemestreByNiveau(Long niveauId){
        return semestreRepository.findByNiveauId(niveauId);
    }

    //verification de l'existence 
    public boolean semestreExist(String nomSemestre,Long niveauId){
        return semestreRepository.existsByNomSemestreAndNiveauId(nomSemestre,niveauId);
    }
}
