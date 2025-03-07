package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CourseModule;
import com.example.demo.repository.ModuleRepository;

@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    public List<CourseModule> getAllModules() {
        return moduleRepository.findAll();
    }

    public CourseModule getModuleById(Long id) {
        return moduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Module non trouvé"));
    }

    public CourseModule saveModule(CourseModule module) {
        return moduleRepository.save(module);
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
}
