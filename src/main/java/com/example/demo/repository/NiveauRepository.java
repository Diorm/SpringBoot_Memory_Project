package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Filiere;
import com.example.demo.model.Niveau;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {

    List<Niveau> findByFiliereId(Long filiereId);
    
    List<Niveau> findByNomNiveau(String nomNiveau);
    
    boolean existsByNomNiveauAndFiliere(String nomNiveau, Filiere filiere);
}

