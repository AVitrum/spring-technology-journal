package com.vitrum.api.service;

import com.vitrum.api.dto.Request.CourseRequest;
import com.vitrum.api.dto.Response.CourseResponse;
import com.vitrum.api.entity.Course;
import com.vitrum.api.entity.User;
import com.vitrum.api.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Course getCourseByName(String name) {
        Optional<Course> course = repository.findByName(name);
        return course.orElse(null);
    }

    public CourseResponse createCourse(CourseRequest courseRequest, UserDetails userDetails) {
        var course = Course.builder()
                .name(courseRequest.getName())
                .description(courseRequest.getDescription())
                .teacher((User) userDetails)
                .build();
        repository.save(course);
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .build();
    }
}
