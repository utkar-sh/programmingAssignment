package com.utkarsh.programmingAssignment.services;

import com.utkarsh.programmingAssignment.models.Professor;
import com.utkarsh.programmingAssignment.models.wrappers.ProfessorDetailWrapper;
import com.utkarsh.programmingAssignment.repository.ProfessorRepository;
import com.utkarsh.programmingAssignment.services.validation.ProfessorValidationService;
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
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorValidationService professorValidationService;
    private final ProfessorRepository professorRepository;

    @Inject
    public ProfessorServiceImpl(ProfessorValidationService professorValidationService, ProfessorRepository professorRepository) {
        this.professorValidationService = professorValidationService;
        this.professorRepository = professorRepository;
    }

    public ResponseEntity<String> createNewProfessor(Professor professor) {
        if (Objects.isNull(professor)) {
            return new ResponseEntity<>("Professor object cannot be empty", HttpStatus.BAD_REQUEST);
        }

        JSONObject validationResult = professorValidationService.validate(professor);

        if (validationResult.has("error")) {
            return new ResponseEntity<>(validationResult.toString(), HttpStatus.CONFLICT);
        }

        try {
            professorRepository.save(professor);

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
    public ResponseEntity<Object> getAllProfessors() {
        try {
            List<Professor> professors = professorRepository.findAll();
            return new ResponseEntity<>(getProfessor(professors), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> editProfessor(Professor editedProfessor) {
        try {
            Optional<Professor> professorOptional = professorRepository.findByCourseTaught(editedProfessor.getCourseTaught());

            if (!professorOptional.isPresent()) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("error", "Unable to find professor who teaches course {" + editedProfessor.getCourseTaught() + "}");
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
            } else {
                Professor professor = professorOptional.get();

                if (Objects.nonNull(editedProfessor.getName())) {
                    professor.setName(editedProfessor.getName());
                }

                if (Objects.nonNull(editedProfessor.getEmail())) {
                    professor.setEmail(editedProfessor.getEmail());
                }

                if (Objects.nonNull(editedProfessor.getSchoolName())) {
                    professor.setSchoolName(editedProfessor.getSchoolName());
                }

                professorRepository.save(professor);

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
    public ResponseEntity<Object> getByCourseTaught(String courseTaught) {

        try {
            Optional<Professor> professorOptional = professorRepository.findByCourseTaught(courseTaught);

            if (!professorOptional.isPresent()) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("error", "Unable to find professor with course taught {" + courseTaught + "}");
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
            } else {
                Professor professor = professorOptional.get();

                    ProfessorDetailWrapper detail = new ProfessorDetailWrapper();
                    detail.setProfessorName(professor.getName());
                    detail.setProfessorEmail(professor.getEmail());
                    detail.setProfessorSchoolName(professor.getSchoolName());
                    detail.setProfessorCourseTaught(professor.getCourseTaught());


                JSONObject jsonObject = new JSONObject();
                jsonObject.append("success", "Product edited successfully");
                return new ResponseEntity<>(detail, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<Object> deleteByName(String name){
        try{
            Optional<Professor> professorOptional = professorRepository.deleteByName(name);

            if (!professorOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(name, HttpStatus.OK);
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private List<ProfessorDetailWrapper> getProfessor(List<Professor> professors) {
        List<ProfessorDetailWrapper> details = new ArrayList<>();

        for (Professor professor : professors) {
            ProfessorDetailWrapper detail = new ProfessorDetailWrapper();
            detail.setProfessorId(professor.getId());
            detail.setProfessorName(professor.getName());
            detail.setProfessorEmail(professor.getEmail());
            detail.setProfessorSchoolName(professor.getSchoolName());
            detail.setProfessorCourseTaught(professor.getCourseTaught());

            details.add(detail);
        }

        return details;
    }
}
