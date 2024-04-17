package com.portal.student.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "course")
public class Course {
    @Id
    private int id;
    private String description;
    private double fee;
    private String title;
}
