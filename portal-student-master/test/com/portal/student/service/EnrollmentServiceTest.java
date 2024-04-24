package com.portal.student.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.portal.student.repository.EnrollmentRepository;
import com.portal.student.repository.StudentRepository;
import com.portal.student.repository.CourseRepository;
import com.portal.student.dto.Course;
import com.portal.student.dto.Student;
import com.portal.student.model.EnrollmentRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

public class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private IntegrationService integrationService;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEnrollInCourse_StudentNotExist() {
        // Arrange
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(1);
        request.setCourseId(1);

        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        String result = enrollmentService.enrollInCourse(request);

        // Assert
        assertEquals("Student does not exist", result);
    }

    @Test
    public void testEnrollInCourse_CourseNotExist() {
        // Arrange
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(1);
        request.setCourseId(1);

        Student student = new Student();
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        String result = enrollmentService.enrollInCourse(request);

        // Assert
        assertEquals("Course does not exist", result);
    }

    @Test
    public void testEnrollInCourse_AlreadyEnrolled() {
        // Arrange
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(1);
        request.setCourseId(1);

        Student student = new Student();
        student.setId(1);

        Course course = new Course();
        course.setId(1);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.countEnrollmentByStudent_IdAndCourseId(1, 1)).thenReturn(1);

        // Act
        String result = enrollmentService.enrollInCourse(request);

        // Assert
        assertEquals("Course already enrolled", result);
    }
}


