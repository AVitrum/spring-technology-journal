package com.vitrum.api.controller;

import com.vitrum.api.dto.Request.CourseRequest;
import com.vitrum.api.dto.Response.CourseResponse;
import com.vitrum.api.entity.Course;
import com.vitrum.api.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CourseRequest courseRequest
    ) {
        return ResponseEntity.ok(service.createCourse(courseRequest, userDetails));
    }


    @GetMapping
    public List<Course> getAllCourses() {
        return service.getAllCourses();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Course> getCourseByName(
            @PathVariable("name") String name
    ) {
        Course course = service.getCourseByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(course);
    }
}
