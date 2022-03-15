package com.utkarsh.programmingAssignment.controllers;

import com.google.inject.Inject;
import com.utkarsh.programmingAssignment.models.Student;
import com.utkarsh.programmingAssignment.services.StudentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {
    private final StudentService studentService;

    @Inject
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> createNewStudent(Student student) {
        return studentService.createNewStudent(student);
    }

    @GetMapping
    public ResponseEntity<Object> getAllStudents() {
        return studentService.getAllStudents();
    }

    @RequestMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> editStudent(Student editedStudent) {
        return studentService.editStudent(editedStudent);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getByName(@PathVariable String name){
        return studentService.getByName(name);
    }

    @GetMapping("/{schoolName}")
    public ResponseEntity<String> getBySchooName(@PathVariable("schoolName") String schoolName) {
        return studentService.getBySchoolName(schoolName);
    }

    @Transactional
    @DeleteMapping("/{name}")
    public ResponseEntity<Object> deleteProfessor(@PathVariable("name") String name) {
        return studentService.deleteByName(name);
    }

}
