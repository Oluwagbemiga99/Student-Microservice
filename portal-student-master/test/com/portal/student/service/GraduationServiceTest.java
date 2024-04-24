package com.portal.student.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.portal.student.repository.StudentRepository;
import com.portal.student.dto.Student;
import com.portal.student.model.Account;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GraduationServiceTest {

    @Mock
    private IntegrationService integrationService;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private GraduationService graduationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGraduationStatus_EligibleToGraduate() {
        // Arrange
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);

        Account account = new Account();
        account.setHasOutstandingBalance(false);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(integrationService.getStudentPaymentStatus(studentId)).thenReturn(account);

        // Act
        String result = graduationService.getGraduationStatus(studentId);

        // Assert
        assertEquals("eligible to graduate", result);
    }

    @Test
    public void testGetGraduationStatus_IneligibleToGraduate() {
        // Arrange
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);

        Account account = new Account();
        account.setHasOutstandingBalance(true);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(integrationService.getStudentPaymentStatus(studentId)).thenReturn(account);

        // Act
        String result = graduationService.getGraduationStatus(studentId);

        // Assert
        assertEquals("ineligible to graduate", result);
    }

    @Test
    public void testGetGraduationStatus_StudentDoesNotExist() {
        // Arrange
        int studentId = 1;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act
        String result = graduationService.getGraduationStatus(studentId);

        // Assert
        assertEquals("Student does not exist", result);
    }
}