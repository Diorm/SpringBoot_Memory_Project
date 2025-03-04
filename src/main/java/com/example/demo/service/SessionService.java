package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Session;
import com.example.demo.repository.SessionRepository;

import lombok.Data;

@Data
@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public Optional <Session> getSession(final Long id){
        return sessionRepository.findById(id);
        
    }
    //Get All
    public Iterable <Session> getSessions(){
        return sessionRepository.findAll();
    }
    //Create and Update

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }
    //delete

    public void deleteSession(final Long id){
        sessionRepository.deleteById(id);

    }

}
