package com.utkarsh.programmingAssignment.services;

import com.utkarsh.programmingAssignment.models.Student;
import com.utkarsh.programmingAssignment.models.wrappers.StudentDetailWrapper;
import com.utkarsh.programmingAssignment.repository.StudentRepository;
import com.utkarsh.programmingAssignment.services.validation.StudentValidationService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Singleton
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentValidationService studentValidationService;
    private final StudentRepository studentRepository;

    @Inject
    public StudentServiceImpl(StudentValidationService studentValidationService, StudentRepository studentRepository) {
        this.studentValidationService = studentValidationService;
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<String> createNewStudent(Student student) {
        if (Objects.isNull(student)) {
            return new ResponseEntity<>("Student object cannot be empty", HttpStatus.BAD_REQUEST);
        }

        JSONObject validationResult = studentValidationService.validate(student);

        if (validationResult.has("error")) {
            return new ResponseEntity<>(validationResult.toString(), HttpStatus.CONFLICT);
        }

        try {
            studentRepository.save(student);

            JSONObject jsonObject = new JSONObject();
            jsonObject.append("success", "Record added successfully");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", "Unable to add record");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<Object> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            return new ResponseEntity<>(getStudent(students), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> editStudent(Student editedStudent) {
        try {
            Optional<Student> studentOptional = studentRepository.findBySchoolName(editedStudent.getName());

            if (!studentOptional.isPresent()) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("error", "Unable to find student with name {" + editedStudent.getName() + "}");
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
            } else {
                Student student = studentOptional.get();

                if (Objects.nonNull(editedStudent.getName())) {
                    student.setName(editedStudent.getName());
                }

                if (Objects.nonNull(editedStudent.getEmail())) {
                    student.setEmail(editedStudent.getEmail());
                }

                if (Objects.nonNull(editedStudent.getSchoolName())) {
                    student.setSchoolName(editedStudent.getSchoolName());
                }

                studentRepository.save(student);

                JSONObject jsonObject = new JSONObject();
                jsonObject.append("success", "Record edited successfully");
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<Object> getByName(String name){
        try{
            Optional<Student> studentOptional = studentRepository.findByName(name);

            if(!studentOptional.isPresent()){
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("error", "Unable to find student by name {" + name + "}");
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
            } else {
                Student student = studentOptional.get();

                StudentDetailWrapper detail = new StudentDetailWrapper();
                detail.setStudentName(student.getName());

                JSONObject jsonObject = new JSONObject();
                jsonObject.append("success", "Student record found");
                return new ResponseEntity<>(detail, HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<String> getBySchoolName(String schoolName) {

        try {
            Optional<Student> studentOptional = studentRepository.findBySchoolName(schoolName);

            if (!studentOptional.isPresent()) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("error", "Unable to find students in school {" + schoolName + "}");
                return new ResponseEntity<>("Students not found", HttpStatus.BAD_REQUEST);
            } else {
                Student student = studentOptional.get();

                StudentDetailWrapper detail = new StudentDetailWrapper();
                detail.setStudentName(student.getName());
                detail.setStudentEmail(student.getEmail());
                detail.setStudentSchoolName(student.getSchoolName());


                JSONObject jsonObject = new JSONObject();
                jsonObject.append("success", "Students found");
                return new ResponseEntity<>(student.getName(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<Object> deleteByName(String name){
        try{
            Optional<Student> studentOptional = studentRepository.deleteByName(name);

            if (!studentOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(name, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private List<StudentDetailWrapper> getStudent(List<Student> students) {
        List<StudentDetailWrapper> details = new ArrayList<>();

        for (Student student : students) {
            StudentDetailWrapper detail = new StudentDetailWrapper();
            detail.setStudentName(student.getName());
            detail.setStudentEmail(student.getEmail());
            detail.setStudentSchoolName(student.getSchoolName());

            details.add(detail);
        }

        return details;
    }
}
