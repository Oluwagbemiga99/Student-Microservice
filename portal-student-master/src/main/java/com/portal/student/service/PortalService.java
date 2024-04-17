package com.portal.student.service;

import com.portal.student.repository.StudentRepository;
import com.portal.student.repository.UserRepository;
import com.portal.student.dto.Student;
import com.portal.student.dto.User;
import com.portal.student.model.Account;
import com.portal.student.model.LoginRequest;
import com.portal.student.model.RegisterRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PortalService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final IntegrationService integrationService;

    public PortalService(StudentRepository studentRepository, UserRepository userRepository, IntegrationService integrationService) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.integrationService = integrationService;
    }

    public String logout(String username){
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            user.setLogin(false);
            userRepository.save(user);
        }

        return "You are successfully logged out";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername());

        if (user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());

            if (isMatch) {
                user.setLogin(true);
                userRepository.save(user);

                return "You are successfully logged in";
            }
        }

        return "Invalid username or password";
    }

    @Transactional
    public String registerStudent(RegisterRequest request) {
        String validation = registerStudentValidation(request);
        if (validation.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(request.getPassword());

            Student student = new Student();
            student.setEmail(request.getEmail());
            student.setFirstname(request.getFirstname());
            student.setLastname(request.getLastname());
            student.setEmail(request.getEmail());
            studentRepository.save(student);

            Student studentFk = studentRepository.findStudentByEmail(request.getEmail());

            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(encodedPassword);
            user.setLogin(false);
            user.setStudent(studentFk);

            userRepository.save(user);

            Account account = new Account();
            account.setStudentId(String.valueOf(studentFk.getId()));

            integrationService.notifyStudentCreated(account);

            return "Student created with ID " + studentFk.getId();
        } else {
            return validation;
        }
    }

    private String registerStudentValidation(RegisterRequest request) {
        if (userRepository.countUserByUsername(request.getUsername()) > 0)
            return "There is already an account with the username: " + request.getUsername();

        if (studentRepository.countStudentByEmail(request.getEmail()) > 0)
            return "There is already an account with the email address: " + request.getEmail();

        return "";
    }
}
