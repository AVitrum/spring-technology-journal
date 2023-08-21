package com.vitrum.api.service;

import com.vitrum.api.dto.Request.TaskRequest;
import com.vitrum.api.dto.Response.TaskResponse;
import com.vitrum.api.entity.Task;
import com.vitrum.api.entity.Topic;
import com.vitrum.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = repository.findAll();
        return tasks.stream().map(this::getTaskResponse).collect(Collectors.toList());
    }

    private TaskResponse getTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .maxScore(task.getMaxScore())
                .taskType(task.getTaskType())
                .creationDate(task.getCreationDate())
                .dueDate(task.getDueDate())
                .topicId(task.getTopic().getId())
                .build();
    }

    public TaskResponse createTask(TaskRequest taskRequest, Topic topic) {
        var task = Task.builder()
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .taskType(taskRequest.getTaskType())
                .maxScore(taskRequest.getMaxScore())
                .dueDate(taskRequest.getDueDate())
                .creationDate(LocalDate.now())
                .topic(topic)
                .build();
        repository.save(task);
        return getTaskResponse(task);
    }
}
