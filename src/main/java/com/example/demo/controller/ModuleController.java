package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CourseModule;
import com.example.demo.service.ModuleService;

@RestController
@RequestMapping("/modules")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @GetMapping
    public List<CourseModule> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/{id}")
    public CourseModule getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @PostMapping
    public CourseModule createModule(@RequestBody CourseModule module) {
        return moduleService.saveModule(module);
    }

    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
    }
}
