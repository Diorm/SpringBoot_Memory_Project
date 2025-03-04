package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Filiere;
import com.example.demo.model.Niveau;
import com.example.demo.repository.NiveauRepository;

import lombok.Data;
@Data
@Service
public class NiveauService {

    @Autowired
    private NiveauRepository niveauRepository;

    // Récupère tous les niveaux
    public List<Niveau> findAll() {
        List<Niveau> niveaux = new ArrayList<>();
        // Convertit l'Iterable retourné par le repository en une List
        niveauRepository.findAll().forEach(niveaux::add);
        return niveaux;
    }

    // Récupère un niveau par son ID
    public Niveau findById(Long id) {
        // Renvoie le niveau s'il existe, sinon renvoie null
        return niveauRepository.findById(id).orElse(null);
    }

    // Récupère tous les niveaux associés à une filière par son ID
    public List<Niveau> getNiveauxByFiliereId(Long filiereId) {
        return niveauRepository.findByFiliereId(filiereId);
    }

    // Récupère tous les niveaux (identique à findAll)
    public Iterable<Niveau> getNiveau() {
        return niveauRepository.findAll();
    }

    // Crée ou met à jour un niveau
    public Niveau saveNiveau(Niveau niveau) {
        // Enregistre le niveau dans la base de données et renvoie l'objet enregistré
        return niveauRepository.save(niveau);
    }

    // Supprime un niveau par son ID
    public void deleteNiveau(final Long id) {
        // Supprime le niveau correspondant à l'ID donné
        niveauRepository.deleteById(id);
    }

    // Met à jour un niveau existant
    public Optional<Niveau> updateNiveau(Long id, Niveau niveau) {
        // Vérifie si le niveau existe déjà
        if (niveauRepository.existsById(id)) {
            niveau.setId(id); // Définit l'ID du niveau à mettre à jour
            return Optional.of(niveauRepository.save(niveau)); // Enregistre et renvoie le niveau mis à jour
        }
        return Optional.empty(); // Renvoie un Optional vide si le niveau n'existe pas
    }

    // Récupère tous les niveaux associés à une filière par son ID (duplicata de getNiveauxByFiliereId)
    public List<Niveau> findByFiliereId(Long filiereId) {
        return niveauRepository.findByFiliereId(filiereId);
    }

    // Vérifie si un niveau avec un nom donné existe déjà dans une filière spécifique
    public boolean existsByNomNiveauAndFiliere(String nomNiveau, Filiere filiere) {
        return niveauRepository.existsByNomNiveauAndFiliere(nomNiveau, filiere);
    }
}