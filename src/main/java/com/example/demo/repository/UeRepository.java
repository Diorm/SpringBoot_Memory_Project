package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Semestre;
import com.example.demo.model.UE;

public interface UeRepository extends JpaRepository<UE, Long> {

    List<UE> findBySemestreId(Long semestreId);
    List<UE> findByNomUE(String nomUE);
    boolean existsByNomUEAndSemestre(String nomUE, Semestre semestre);
    
}
