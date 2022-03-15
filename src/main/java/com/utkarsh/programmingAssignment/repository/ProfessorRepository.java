package com.utkarsh.programmingAssignment.repository;

import com.utkarsh.programmingAssignment.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    Optional<Professor> findByCourseTaught(String courseTaught);
    Optional<Professor> deleteByName(String name);
}
