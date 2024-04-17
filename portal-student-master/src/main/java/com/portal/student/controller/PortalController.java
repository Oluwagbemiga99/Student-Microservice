package com.portal.student.controller;

import com.portal.student.service.PortalService;
import com.portal.student.model.LoginRequest;
import com.portal.student.model.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortalController {
    private final PortalService portalService;

    public PortalController(PortalService portalService) {
        this.portalService = portalService;
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@Valid RegisterRequest request) {
        String result = portalService.registerStudent(request);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid LoginRequest request) {
        String result = portalService.login(request);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    ResponseEntity<?> logout(@Valid String username) {
        String result = portalService.logout(username);

        return ResponseEntity.ok(result);
    }
}
