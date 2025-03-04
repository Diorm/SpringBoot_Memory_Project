package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Session;


@Repository
public interface SessionRepository  extends JpaRepository<Session,Long>{

}
