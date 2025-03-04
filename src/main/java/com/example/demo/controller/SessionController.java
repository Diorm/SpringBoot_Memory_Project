package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Session;
import com.example.demo.service.SessionService;

import java.util.Optional;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

   @PostMapping
public ResponseEntity<Session> createSession(@RequestBody Session session) {
    if (session.getNomSession() == null || session.getNomSession().isEmpty()) {
        return ResponseEntity.badRequest().build(); // Retourne une erreur 400 si nomSession est nul ou vide
    }
    Session createdSession = sessionService.createSession(session);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
}


    @GetMapping("/{id}")
    public ResponseEntity<Session> getSession(@PathVariable Long id) {
        Optional<Session> session = sessionService.getSession(id);
        return session.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Iterable<Session> getSessions() {
        return sessionService.getSessions();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody Session session) {
        // Mettez à jour la session ici
        // Vous devez d'abord vérifier si la session existe
        if (sessionService.getSession(id).isPresent()) {
            session.setId(id); // Assurez-vous que l'ID est défini
            return ResponseEntity.ok(sessionService.createSession(session));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}