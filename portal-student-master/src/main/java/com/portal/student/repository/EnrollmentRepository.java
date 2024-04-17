package com.portal.student.repository;

import com.portal.student.dto.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    int countEnrollmentByStudent_IdAndCourseId(int studentId, int courseId);
    Enrollment findEnrollmentByStudent_IdAndCourseId(int studentId, int courseId);
    List<Enrollment> findAllByStudent_Id(int studentId);
}
