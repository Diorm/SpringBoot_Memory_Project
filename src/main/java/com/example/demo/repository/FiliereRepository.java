package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Filiere;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long>{

    Optional<Filiere> findByNomFiliere(String nomFiliere);

}
