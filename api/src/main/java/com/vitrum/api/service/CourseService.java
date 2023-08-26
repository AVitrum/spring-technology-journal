package com.vitrum.api.service;

import com.vitrum.api.dto.Request.CourseRequest;
import com.vitrum.api.dto.Response.CourseResponse;
import com.vitrum.api.dto.Response.TopicResponse;
import com.vitrum.api.entity.Course;
import com.vitrum.api.entity.Topic;
import com.vitrum.api.entity.User;
import com.vitrum.api.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public List<CourseResponse> getAllCourses() {
        List<Course> courses = repository.findAll();

        return courses.stream().map(course -> getCourseResponse(course, getTopicResponses(course))).collect(Collectors.toList());
    }

    public CourseResponse getCourseResponseByName(String name) {
        Optional<Course> optionalCourse = repository.findByName(name);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            List<TopicResponse> topicResponses = getTopicResponses(course);

            return getCourseResponse(course, topicResponses);
        }

        return null;
    }

    public Course getCourseByName(String name) {
        Optional<Course> optionalCourse = repository.findByName(name);
        return optionalCourse.orElse(null);
    }

    public CourseResponse createCourse(CourseRequest courseRequest, UserDetails userDetails) {
        Course existingCourse = repository.findByName(courseRequest.getName()).orElse(null);
        if (existingCourse != null) {
            throw new IllegalArgumentException("Course with the same name already exists.");
        }
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
                .userId(course.getTeacher().getId())
                .build();
    }

    private CourseResponse getCourseResponse(Course course, List<TopicResponse> topicResponses) {
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .userId(course.getTeacher().getId())
                .topics(topicResponses)
                .build();
    }

    private List<TopicResponse> getTopicResponses(Course course) {
        List<Topic> topics = course.getTopics();
        return topics.stream()
                .map(this::mapTopicToTopicResponse)
                .collect(Collectors.toList());
    }

    private TopicResponse mapTopicToTopicResponse(Topic topic) {
        return TopicResponse.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .additionalResources(topic.getAdditionalResources())
                .courseId(topic.getCourse().getId())
                .build();
    }
}
