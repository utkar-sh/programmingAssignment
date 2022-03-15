package com.utkarsh.programmingAssignment.services;

import com.utkarsh.programmingAssignment.models.Student;
import org.springframework.http.ResponseEntity;

public interface StudentService {
    ResponseEntity<String> createNewStudent(Student student);

    ResponseEntity<Object> getAllStudents();

    ResponseEntity<String> editStudent(Student editedStudent);

    ResponseEntity<Object> getByName(String name);

    ResponseEntity<String> getBySchoolName(String schoolName);

    ResponseEntity<Object> deleteByName(String name);
}
