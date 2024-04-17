package com.portal.student.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRequest {
    @NotNull(message = "studentId cannot be empty")
    private int studentId;
    @NotBlank(message = "firstname cannot be empty")
    private String firstname;
    @NotBlank(message = "lastname cannot be empty")
    private String lastname;
}
