package com.doortodoor.services;

import com.doortodoor.dto.TaskDto;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    UUID createTask (TaskDto taskDto);
    TaskDto getTask (UUID id);
    List<TaskDto> getAllTasks (UUID organizationId);
}
