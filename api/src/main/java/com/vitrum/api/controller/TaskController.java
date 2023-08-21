package com.vitrum.api.controller;

import com.vitrum.api.dto.Request.TaskRequest;
import com.vitrum.api.dto.Response.TaskResponse;
import com.vitrum.api.entity.Course;
import com.vitrum.api.entity.Topic;
import com.vitrum.api.service.CourseService;
import com.vitrum.api.service.TaskService;
import com.vitrum.api.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;
    private final TopicService topicService;
    private final CourseService courseService;

    @PostMapping({"/{courseName}/{topicName}"})
    public ResponseEntity<TaskResponse> addTaskToCourse(
            @PathVariable String courseName,
            @PathVariable String topicName,
            @RequestBody TaskRequest taskRequest
    ) {
        taskRequest.setCourseName(courseName);
        taskRequest.setTopicName(topicName);
        Course course = courseService.getCourseByName(courseName);
        Topic topic = topicService.getTopicByNameAndCourse(topicName, course);
        return ResponseEntity.ok(service.createTask(taskRequest, topic));
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return service.getAllTasks();
    }
}
