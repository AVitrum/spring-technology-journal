package com.vitrum.api.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    private String name;
    private String description;
    private String courseName;
    private String topicName;
    private String taskType;
    private LocalDate dueDate;
    private int maxScore;
}
