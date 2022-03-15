package com.utkarsh.programmingAssignment.controllers;

import com.google.inject.Inject;
import com.utkarsh.programmingAssignment.models.Professor;
import com.utkarsh.programmingAssignment.services.ProfessorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/professors")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProfessorController {
    private final ProfessorService professorService;

    @Inject
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> createNewProfessor(Professor professor) {
        return professorService.createNewProfessor(professor);
    }

    @GetMapping
    public ResponseEntity<Object> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @RequestMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> editProfessor(Professor editedProfessor) {
        return professorService.editProfessor(editedProfessor);
    }

    @GetMapping("/{courseTaught}")
    public ResponseEntity<Object> getByCourseTaught(@PathVariable String courseTaught) {
        return professorService.getByCourseTaught(courseTaught);
    }

    @Transactional
    @DeleteMapping("/{name}")
    public ResponseEntity<Object> deleteProfessor(@PathVariable("name") String name) {
        return professorService.deleteByName(name);
    }

}
