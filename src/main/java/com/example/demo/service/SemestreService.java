package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Niveau;
import com.example.demo.model.Semestre;
import com.example.demo.repository.SemestreRepository;

@Service
public class SemestreService {

    @Autowired
    private SemestreRepository semestreRepository;



    // Récupère tous les niveaux
    public List<Semestre> findAll() {
        List<Semestre> semes = new ArrayList<>();
        // Convertit l'Iterable retourné par le repository en une List
        semestreRepository.findAll().forEach(semes::add);
        return semes;
    }

    // Récupère un niveau par son ID
    public Semestre findById(Long id) {
        // Renvoie le niveau s'il existe, sinon renvoie null
        return semestreRepository.findById(id).orElse(null);
    }


      // Récupère tous les semestres associés à un  niveau par son ID
      public List<Semestre> getSemestres(Long niveauId) {
        return semestreRepository.findByNiveauId(niveauId);
    }


    // Récupère tous les semestres (identique à findAll)
    public Iterable<Semestre> getSemestre() {
        return semestreRepository.findAll();
    }


    //Creer ou met à jour un semestre
    public Semestre saveSemestre(Semestre semestre) {
        // Enregistre le semestre dans la base de données et renvoie l'objet enregistré
        return semestreRepository.save(semestre);
    }

    // Supprime un semestre par son ID
    public void deleteSemestre(final Long id) {
        // Supprime le semestre correspondant à l'ID donné
        semestreRepository.deleteById(id);
    }


    // Met à jour un semestre existant
    public Semestre updateSemestre(Long id, Semestre semestre) {
        // Vérifie si le semestre existe déjà
        if (semestreRepository.existsById(id)) {
            // Met à jour le semestre
            semestre.setId(id);
            return semestreRepository.save(semestre);
        }
        return null;
    }


    // Récupère tous les semestres associés à un niveau par son ID (duplicata de getSemestres)
    public List<Semestre> findByNiveauId(Long niveauId) {
        return semestreRepository.findByNiveauId(niveauId);
    }


    //Vérifie si un semestre existe
    public boolean existsByNomSemestreAndNiveauId(String nomSemestre, Niveau niveau) {
        return semestreRepository.existsByNomSemestreAndNiveau(nomSemestre, niveau);
    }




}