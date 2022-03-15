package com.utkarsh.programmingAssignment.services.validation;

import com.utkarsh.programmingAssignment.models.Professor;
import com.utkarsh.programmingAssignment.repository.ProfessorRepository;
import com.utkarsh.programmingAssignment.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Service
public class ProfessorValidationService {

    private final ProfessorRepository professorRepository;

    @Inject
    public ProfessorValidationService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }


    public JSONObject validate(Professor professor) {
        JSONObject result = new JSONObject();
        JSONObject errors = new JSONObject();

        if (StringUtils.isNullOrEmpty(professor.getName())) {
            errors.put("productCode", "Product Code cannot be empty");
        } else if (!isProductAlreadyRegistered(professor)) {
            errors.put("productCode", "Product Code is already registered");
        }

        if (StringUtils.isNullOrEmpty(professor.getName())) {
            errors.put("name", "Product Name cannot be empty");
        }

        if (!errors.isEmpty()) {
            result.put("error", errors);
        } else {
            result.append("success", "Valid User Details");
        }

        return result;
    }

    private boolean isProductAlreadyRegistered(Professor professor) {
        List<Professor> professors = professorRepository.findAll();

        long count = professors.stream().filter(p -> professor.getName().equals(p.getName())).count();

        return count == 0;
    }
}
