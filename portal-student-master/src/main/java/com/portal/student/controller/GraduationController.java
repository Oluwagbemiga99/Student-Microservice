package com.portal.student.controller;

import com.portal.student.service.GraduationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graduation")
public class GraduationController {
    private final GraduationService graduationService;

    public GraduationController(GraduationService graduationService) {
        this.graduationService = graduationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGraduationStatus(@PathVariable int id) {
        String result = graduationService.getGraduationStatus(id);

        return ResponseEntity.ok(result);
    }
}
