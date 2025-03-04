package com.example.demo.service;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Filiere;
import com.example.demo.repository.FiliereRepository;

import lombok.Data;





@Data
@Service
public class FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;

    public Optional <Filiere> getFiliere(final Long id){
        return filiereRepository.findById(id);
        
    }


    //get All

    public Iterable <Filiere> getFiliere(){
        return filiereRepository.findAll();
    }


    //Create

    public Filiere creatFiliere(Filiere filiere){
        return filiereRepository.save(filiere);
    }

    //Delete

    public void deleteFiliere(final Long id){
        filiereRepository.deleteById(id);
    }

    // Update
    public Optional<Filiere> updateFiliere(Long id, Filiere filiere) {
    if (filiereRepository.existsById(id)) {
        filiere.setId(id);
        return Optional.of(filiereRepository.save(filiere)); 
    }
        return Optional.empty();
    }



    //
    public Filiere findById(Long id) {
        return filiereRepository.findById(id).orElse(null);
    }


    
    
}
