package com.portal.student.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.portal.student.repository.StudentRepository;
import com.portal.student.dto.Student;
import com.portal.student.model.UpdateRequest;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProfileById_StudentExists() {
        // Arrange
        int studentId = 1;
        Student expectedStudent = new Student();
        expectedStudent.setId(studentId);
        expectedStudent.setFirstname("John");
        expectedStudent.setLastname("Doe");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

        // Act
        Student result = profileService.getProfileById(studentId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStudent, result);
    }

    @Test
    public void testGetProfileById_StudentDoesNotExist() {
        // Arrange
        int studentId = 1;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act
        Student result = profileService.getProfileById(studentId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testUpdateProfile_Success() {
        // Arrange
        int studentId = 1;
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setStudentId(studentId);
        updateRequest.setFirstname("John");
        updateRequest.setLastname("Smith");

        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstname("John");
        existingStudent.setLastname("Doe");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        // Act
        String result = profileService.updateProfile(updateRequest);

        // Assert
        assertEquals("Profile successfully updated", result);

        assertEquals("John", existingStudent.getFirstname());
        assertEquals("Smith", existingStudent.getLastname());
    }

    @Test
    public void testUpdateProfile_StudentDoesNotExist() {
        // Arrange
        int studentId = 1;
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setStudentId(studentId);
        updateRequest.setFirstname("John");
        updateRequest.setLastname("Smith");

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act
        String result = profileService.updateProfile(updateRequest);

        // Assert
        assertEquals("Student does not exist", result);
    }
}