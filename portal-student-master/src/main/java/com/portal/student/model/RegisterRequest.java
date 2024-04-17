package com.portal.student.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "username cannot be empty")
    @Size(max = 10, message = "username max length is 10")
    private String username;
    @NotBlank(message = "password cannot be empty")
    @Size(max = 15, message = "password max length is 15")
    private String password;
    @NotBlank(message = "email cannot be empty")
    @Size(max = 50, message = "email max length is 50")
    private String email;
    private String firstname;
    private String lastname;
}
