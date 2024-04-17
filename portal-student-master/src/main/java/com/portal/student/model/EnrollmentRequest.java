package com.portal.student.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequest {
    @NotNull(message = "studentId cannot be empty")
    private int studentId;
    @NotNull(message = "courseId cannot be empty")
    private int courseId;
}
