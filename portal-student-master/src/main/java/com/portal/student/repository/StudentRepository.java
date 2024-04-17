package com.portal.student.repository;

import com.portal.student.dto.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    int countStudentByEmail(String email);
    Student findStudentByEmail(String email);
}
