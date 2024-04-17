package com.portal.student.service;

import com.portal.student.repository.CourseRepository;
import com.portal.student.repository.EnrollmentRepository;
import com.portal.student.repository.StudentRepository;
import com.portal.student.dto.Course;
import com.portal.student.dto.Enrollment;
import com.portal.student.dto.Student;
import com.portal.student.model.Account;
import com.portal.student.model.EnrollmentRequest;
import com.portal.student.model.Invoice;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final IntegrationService integrationService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository, IntegrationService integrationService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.integrationService = integrationService;
    }

    public Enrollment getEnrolledCourse(int id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        return enrollment.orElse(null);
    }

    public List<Enrollment> getAllEnrolledCourse(int id) {
        return enrollmentRepository.findAllByStudent_Id(id);
    }

    @Transactional
    public String enrollInCourse(EnrollmentRequest request) {
        Optional<Student> studentDb = studentRepository.findById(request.getStudentId());

        if (studentDb.isPresent()) {
            Optional<Course> courseDb = courseRepository.findById(request.getCourseId());
            if (courseDb.isPresent()) {
                Student student = studentDb.get();
                Course course = courseDb.get();

                if (enrollmentRepository.countEnrollmentByStudent_IdAndCourseId(student.getId(), course.getId()) > 0) {
                    return "Course already enrolled";
                }

                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);

                enrollmentRepository.save(enrollment);

                Account account = new Account();
                account.setStudentId(String.valueOf(student.getId()));

                Invoice invoice = new Invoice();
                invoice.setAccount(account);
                invoice.setType(Invoice.Type.TUITION_FEES);
                invoice.setDueDate(LocalDate.now().plusMonths(1));
                invoice.setAmount(course.getFee());

                integrationService.createCourseFeeInvoice(invoice);

                Enrollment savedEnrollment = enrollmentRepository.findEnrollmentByStudent_IdAndCourseId(student.getId(), course.getId());

                return "Course successfully enrolled, ID " + savedEnrollment.getId();
            }

            return "Course is not exist";
        }

        return "Student is not exist";
    }
}
