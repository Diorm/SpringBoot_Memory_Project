package com.example.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Semestre;
import com.example.demo.model.UE;
import com.example.demo.repository.UeRepository;



@Service
public class UEService {

    @Autowired
    private UeRepository ueRepository;

    public List<UE> findAll() {
        List<UE> ues = new ArrayList<>();
        ueRepository.findAll().forEach(ues::add); // Convertir Iterable en List
        return ues;
    }



    public UE findById(Long id) {
        return ueRepository.findById(id).orElse(null);
    }


    // Récupère tous les UE associés à un semestre par son ID
    public List<UE> getUE(Long semestreId) {
        return ueRepository.findBySemestreId(semestreId);
    }


    // Récupère tous les UE (identique à findAll)
    public Iterable<UE> getUE() {
        return ueRepository.findAll();
    }

    // Crée ou met à jour un UE
    public UE saveUE(UE ue) {
        return ueRepository.save(ue);
    }

    // Supprime un UE par son ID
    public void deleteUE(final Long id) {
        ueRepository.deleteById(id);
    }

    // Met à jour un UE existant
    public UE updateUE(Long id, UE ue) {
        if (ueRepository.existsById(id)) {
            ue.setId(id);
            return ueRepository.save(ue);
        }
        return null;
    }

    //Recuperer tous les UE associés à un semestre par son ID
    public List<UE> findBySemestreId(Long semestreId) {
        return ueRepository.findBySemestreId(semestreId);
    }


    //Verifier si un UE existe déjà
    public boolean existsByNomUEAndSemestreId(String nomUE, Semestre semestre) {
        return ueRepository.existsByNomUEAndSemestre(nomUE, semestre);
    }
}