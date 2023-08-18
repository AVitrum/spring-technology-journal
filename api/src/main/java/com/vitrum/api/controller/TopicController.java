package com.vitrum.api.controller;

import com.vitrum.api.dto.Request.TopicRequest;
import com.vitrum.api.dto.Response.TopicResponse;
import com.vitrum.api.entity.Course;
import com.vitrum.api.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService service;

    @PostMapping("/{courseName}")
    public ResponseEntity<TopicResponse> addTopicToCourse(
            @PathVariable String courseName,
            @RequestBody TopicRequest topicRequest
    ) {
        topicRequest.setCourseName(courseName);
        Course course = service.getCourseByName(topicRequest.getCourseName());
        return ResponseEntity.ok(service.createTopic(topicRequest, course));
    }

    @GetMapping
    public List<TopicResponse> getAllTopics() {
        return service.getAllTopics();
    }

    @GetMapping("/{courseName}")
    public List<TopicResponse> getAllTopicsByCourse(
            @PathVariable String courseName
    ) {
        Course course = service.getCourseByName(courseName);
        return service.getAllTopicsByCourse(course);
    }
}
