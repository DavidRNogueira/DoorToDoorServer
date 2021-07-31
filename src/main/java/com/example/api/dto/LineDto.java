package com.example.api.dto;

import java.util.List;

public class LineDto {
    private String id;
    private List<CoordinatesDto> coordinates;
    private TaskDto task;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CoordinatesDto> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<CoordinatesDto> coordinates) {
        this.coordinates = coordinates;
    }

    public TaskDto getTask() {
        return task;
    }

    public void setTask(TaskDto task) {
        this.task = task;
    }
}
