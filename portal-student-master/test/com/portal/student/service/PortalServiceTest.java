package com.portal.student.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.portal.student.repository.StudentRepository;
import com.portal.student.repository.UserRepository;
import com.portal.student.dto.User;
import com.portal.student.model.LoginRequest;
import com.portal.student.model.RegisterRequest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PortalServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IntegrationService integrationService;

    @InjectMocks
    private PortalService portalService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogout_Success() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setLogin(true);

        when(userRepository.findUserByUsername(username)).thenReturn(user);

        // Act
        String result = portalService.logout(username);

        // Assert
        assertEquals("You are successfully logged out", result);
        assertFalse(user.isLogin()); // user should be logged out
    }

    @Test
    public void testLogin_Success() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password)); // encoded password

        when(userRepository.findUserByUsername(username)).thenReturn(user);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        // Act
        String result = portalService.login(request);

        // Assert
        assertEquals("You are successfully logged in", result);
        assertTrue(user.isLogin());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        // Arrange
        String username = "testUser";
        String password = "wrongPassword";

        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode("correctPassword")); // different encoded password

        when(userRepository.findUserByUsername(username)).thenReturn(user);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        // Act
        String result = portalService.login(request);

        // Assert
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void testRegisterStudent_UsernameAlreadyExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existingUser");
        request.setPassword("password");
        request.setEmail("student@example.com");

        when(userRepository.countUserByUsername("existingUser")).thenReturn(1); // user with this username exists

        // Act
        String result = portalService.registerStudent(request);

        // Assert
        assertEquals("There is already an account with the username: existingUser", result);
    }
}