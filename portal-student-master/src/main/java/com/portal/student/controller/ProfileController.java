package com.portal.student.controller;

import com.portal.student.dto.Student;
import com.portal.student.model.UpdateRequest;
import com.portal.student.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getProfileById(@PathVariable int id) {
        Student result = profileService.getProfileById(id);

        if (result == null) {
            return ResponseEntity.ok("Student does not exist");
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping
    ResponseEntity<?> updateProfile(@Valid UpdateRequest request) {
        String result = profileService.updateProfile(request);

        return ResponseEntity.ok(result);
    }
}
