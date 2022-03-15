package com.utkarsh.programmingAssignment.services.validation;

import com.utkarsh.programmingAssignment.models.Student;
import com.utkarsh.programmingAssignment.repository.StudentRepository;
import com.utkarsh.programmingAssignment.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Service
public class StudentValidationService {

    private final StudentRepository studentRepository;

    @Inject
    public StudentValidationService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public JSONObject validate(Student student) {
        JSONObject result = new JSONObject();
        JSONObject errors = new JSONObject();

        if (StringUtils.isNullOrEmpty(student.getName())) {
            errors.put("name", "Student name cannot be empty");
        } else if (!isStudentAlreadyExists(student)) {
            errors.put("name", "Student already exists");
        }

        if (StringUtils.isNullOrEmpty(student.getName())) {
            errors.put("email", "Student email cannot be empty");
        }

        if (!errors.isEmpty()) {
            result.put("error", errors);
        } else {
            result.append("success", "Valid Student Details");
        }

        return result;
    }

    private boolean isStudentAlreadyExists(Student student) {
        List<Student> students = studentRepository.findAll();

        long count = students.stream().filter(p -> student.getName().equals(p.getName())).count();

        return count == 0;
    }
}
