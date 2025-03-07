package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CourseModule;

@Repository
public interface ModuleRepository extends JpaRepository<CourseModule, Long> {

    List<Module> findByUeId(Long ueId);

}
