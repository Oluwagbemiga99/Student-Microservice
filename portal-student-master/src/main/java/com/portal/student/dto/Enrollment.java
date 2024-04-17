package com.portal.student.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}
