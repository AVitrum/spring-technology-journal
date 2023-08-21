package com.vitrum.api.controller;

import com.vitrum.api.dto.Request.CourseRequest;
import com.vitrum.api.dto.Response.CourseResponse;
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
        CourseResponse response = service.createCourse(courseRequest, userDetails);
        if (response == null)
            return ResponseEntity.status(HttpStatus.FOUND).body(null);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public List<CourseResponse> getAllCourses() {
        return service.getAllCourses();
    }

    @GetMapping("/{name}")
    public ResponseEntity<CourseResponse> getCourseByName(
            @PathVariable("name") String name
    ) {
        CourseResponse courseResponse = service.getCourseResponseByName(name);

        if (courseResponse != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(courseResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
