package com.portal.student.service;

import com.portal.student.repository.CourseRepository;
import com.portal.student.dto.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> viewAll() {
        return courseRepository.findAll();
    }
}
