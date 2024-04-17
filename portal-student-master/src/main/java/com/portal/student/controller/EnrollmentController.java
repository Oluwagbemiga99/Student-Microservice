package com.portal.student.controller;

import com.portal.student.dto.Enrollment;
import com.portal.student.model.EnrollmentRequest;
import com.portal.student.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<?> enrollInCourse(@Valid EnrollmentRequest request) {
        String result = enrollmentService.enrollInCourse(request);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllEnrolledCourse(@PathVariable int id) {
        List<Enrollment> result = enrollmentService.getAllEnrolledCourse(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnrolledCourse(@PathVariable int id) {
        Enrollment result = enrollmentService.getEnrolledCourse(id);

        return ResponseEntity.ok(result);
    }
}
