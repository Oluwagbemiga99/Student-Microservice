package com.portal.student.controller;

import com.portal.student.dto.Course;
import com.portal.student.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    ResponseEntity<?> viewAll() {
        List<Course> courseList = courseService.viewAll();

        return ResponseEntity.ok(courseList);
    }
}
