package com.utkarsh.programmingAssignment.repository;

import com.utkarsh.programmingAssignment.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByName(String name);

    @Query(value = "select name from Students where schoolName = ?1", nativeQuery = true)
    Optional<Student> findBySchoolName(String schoolName);

    Optional<Student> deleteByName(String name);
}
