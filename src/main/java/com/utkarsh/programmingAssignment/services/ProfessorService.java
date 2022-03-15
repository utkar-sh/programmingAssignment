package com.utkarsh.programmingAssignment.services;

import com.utkarsh.programmingAssignment.models.Professor;
import org.springframework.http.ResponseEntity;

public interface ProfessorService {
    ResponseEntity<String> createNewProfessor(Professor professor);

    ResponseEntity<Object> getAllProfessors();

    ResponseEntity<String> editProfessor(Professor editedProfessor);

    ResponseEntity<Object> getByCourseTaught(String courseTaught);

    ResponseEntity<Object> deleteByName(String name);
}
