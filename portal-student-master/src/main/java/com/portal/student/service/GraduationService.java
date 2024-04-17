package com.portal.student.service;

import com.portal.student.repository.StudentRepository;
import com.portal.student.dto.Student;
import com.portal.student.model.Account;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GraduationService {
    private final IntegrationService integrationService;
    private final StudentRepository studentRepository;

    public GraduationService(IntegrationService integrationService, StudentRepository studentRepository) {
        this.integrationService = integrationService;
        this.studentRepository = studentRepository;
    }

    public String getGraduationStatus(int id) {
        Optional<Student> studentDb = studentRepository.findById(id);

        if (studentDb.isPresent()) {
            Account account = integrationService.getStudentPaymentStatus(id);

            return account.isHasOutstandingBalance() ? "ineligible to graduate" : "eligible to graduate";
        }

        return "Student does not exist";
    }
}
