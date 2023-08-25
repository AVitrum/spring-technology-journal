package com.vitrum.api.controller;

import com.vitrum.api.dto.Request.ResultRequest;
import com.vitrum.api.dto.Response.ResultResponse;
import com.vitrum.api.entity.Task;
import com.vitrum.api.entity.User;
import com.vitrum.api.service.ResultService;
import com.vitrum.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService service;
    private final TaskService taskService;

    @PostMapping("/{taskName}")
    public ResponseEntity<ResultResponse> addAnswerToTask(
            @PathVariable String taskName,
            @RequestBody ResultRequest resultRequest,
            @AuthenticationPrincipal UserDetails user
    ) {
        Task task = taskService.getTaskByName(taskName);
        resultRequest.setTask(task);
        resultRequest.setStudent((User) user);
        return ResponseEntity.ok(service.addAnswerToTask(resultRequest));
    }

    @GetMapping
    public List<ResultResponse> getAllUserResults(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return service.getAllUserResults((User) userDetails);
    }
}
