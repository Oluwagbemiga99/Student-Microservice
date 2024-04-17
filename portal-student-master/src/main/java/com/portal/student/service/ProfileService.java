package com.portal.student.service;

import com.portal.student.repository.StudentRepository;
import com.portal.student.dto.Student;
import com.portal.student.model.UpdateRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProfileService {
    private final StudentRepository studentRepository;

    public ProfileService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getProfileById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.orElse(null);
    }

    public String updateProfile(UpdateRequest updateRequest) {
        Optional<Student> studentObj = studentRepository.findById(updateRequest.getStudentId());

        if (studentObj.isPresent()) {
            Student student = studentObj.get();
            student.setFirstname(updateRequest.getFirstname());
            student.setLastname(updateRequest.getLastname());

            studentRepository.save(student);
            return "Profile successfully updated";
        }

        return "Student does not exist";
    }
}
