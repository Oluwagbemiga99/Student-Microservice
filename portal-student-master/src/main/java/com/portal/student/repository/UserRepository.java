package com.portal.student.repository;

import com.portal.student.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    int countUserByUsername(String email);
    User findUserByUsername(String email);
}
