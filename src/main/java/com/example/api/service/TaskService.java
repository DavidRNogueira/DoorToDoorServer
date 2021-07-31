package com.example.api.service;

import com.example.api.dto.LineDto;
import com.example.api.dto.TaskDto;
import javassist.NotFoundException;

public interface TaskService {
    String createTask (TaskDto taskDto);
    void deleteTask (String taskId) throws NotFoundException;
    TaskDto getTaskById (String id);
    void updateTask (TaskDto taskDto) throws NotFoundException;
    void createNewLine (LineDto lineDto, String taskId);
}
