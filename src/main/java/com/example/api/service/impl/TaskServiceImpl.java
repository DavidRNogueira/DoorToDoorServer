package com.example.api.service.impl;

import com.example.api.dao.OrgDao;
import com.example.api.dto.CoordinatesDto;
import com.example.api.dto.LineDto;
import com.example.api.dto.OrganizationDto;
import com.example.api.dto.TaskDto;
import com.example.api.entity.CoordinatesEntity;
import com.example.api.entity.LineEntity;
import com.example.api.entity.OrganizationEntity;
import com.example.api.entity.TaskEntity;
import com.example.api.service.TaskService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    final public static String INCOMPLETE = "incomplete";
    final public static String COMPLETE = "complete";
    final public static String QUEUE = "queue";
    private OrgDao orgDao;

    public TaskServiceImpl(OrgDao orgDao) {
        this.orgDao = orgDao;
    }

    public String createTask(TaskDto taskDto) {
        TaskEntity taskEntity = new TaskEntity();
        OrganizationEntity organizationEntity = orgDao.getOrgById(taskDto.getOrgFk());

        taskEntity.setAddress(taskDto.getAddress());
        taskEntity.setCity(taskDto.getCity());
        taskEntity.setState(taskDto.getState());
        taskEntity.setOrg(organizationEntity);
        taskEntity.setName(taskDto.getName());
        taskEntity.setStatus(INCOMPLETE);

        if (taskDto.getCoordinates() != null) {
            for (CoordinatesDto coordinatesDto : taskDto.getCoordinates()) {
                CoordinatesEntity coordinatesEntity = new CoordinatesEntity();
                coordinatesEntity.setLng(coordinatesDto.getLng());
                coordinatesEntity.setLat(coordinatesDto.getLat());
                coordinatesEntity.setOrderNumber(coordinatesDto.getOrderNumber());
                taskEntity.addCoordinate(coordinatesEntity);
            }
        }

        return orgDao.createTask(taskEntity);
    }

    public void deleteTask(String taskId) throws NotFoundException {
        TaskEntity task = orgDao.getTaskById(taskId);

        if (task == null) {
            throw new NotFoundException("This task does not exist");
        }

        if (task.getCoordinates() != null) {
            task.removeCoordinates(task.getCoordinates());
        }

        if (task.getLines() != null) {
            task.removeLines(task.getLines());
        }

        orgDao.updateTask(task);
        orgDao.deleteTask(taskId);
    }

    public TaskDto getTaskById (String id) {
        TaskEntity taskEntity = orgDao.getTaskById(id);

//        Check if task was sent less than 12 hours ago
        Boolean isTaskExpired = taskEntity.getTimeSent() == null || ((new Date().getTime() - taskEntity.getTimeSent()) > 43200000);

        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(taskEntity.getOrgFk().getId());
        organizationDto.setName(taskEntity.getOrgFk().getName());

        TaskDto taskDto = new TaskDto();
        taskDto.setAddress(taskEntity.getAddress());
        taskDto.setCity(taskEntity.getCity());
        taskDto.setState(taskEntity.getState());
        taskDto.setName(taskEntity.getName());
        taskDto.setId(id);
        taskDto.setStatus(taskEntity.getStatus());
        taskDto.setOrganization(organizationDto);
        taskDto.setExpired(isTaskExpired);
        taskDto.setTimeSent(taskEntity.getTimeSent());
        taskDto.setSalvations(taskEntity.getSalvations());
        taskDto.setFirstName(taskEntity.getFirstName());
        taskDto.setLastName(taskEntity.getLastName());
        taskDto.setPhoneNumber(taskEntity.getPhoneNumber());

        List<CoordinatesDto> coordinatesDtoList = taskEntity.getCoordinates()
                .stream()
                .map(coordinatesEntity -> {
                    CoordinatesDto coordinatesDto = new CoordinatesDto();
                    coordinatesDto.setId(coordinatesEntity.getId());
                    coordinatesDto.setLat(coordinatesEntity.getLat());
                    coordinatesDto.setLng(coordinatesEntity.getLng());
                    coordinatesDto.setOrderNumber(coordinatesEntity.getOrderNumber());
                    return coordinatesDto;
                })
                .collect(Collectors.toList());
        taskDto.setCoordinates(coordinatesDtoList);

        List<LineDto> lineDtos = taskEntity.getLines()
                .stream()
                .map(lineEntity -> {
                    LineDto lineDto = new LineDto();
                    lineDto.setId(lineEntity.getId());
                    List<CoordinatesDto> coordinates = lineEntity.getCoordinates()
                            .stream()
                            .map(coordinatesEntity -> {
                                CoordinatesDto coordinatesDto = new CoordinatesDto();
                                coordinatesDto.setId(coordinatesEntity.getId());
                                coordinatesDto.setLat(coordinatesEntity.getLat());
                                coordinatesDto.setLng(coordinatesEntity.getLng());
                                coordinatesDto.setOrderNumber(coordinatesEntity.getOrderNumber());
                                return coordinatesDto;
                            })
                            .collect(Collectors.toList());
                    lineDto.setCoordinates(coordinates);
                    return lineDto;
                })
                .collect(Collectors.toList());
        taskDto.setLines(lineDtos);

        return taskDto;
    }

    public void updateTask (TaskDto taskDto) throws NotFoundException {
        TaskEntity taskEntity = null;
        OrganizationEntity organizationEntity = orgDao.getOrgById(taskDto.getOrganization().getId());

        for (TaskEntity taskFromOrg: organizationEntity.getTasks()) {
            if (taskFromOrg.getId().equals(taskDto.getId())) {
                taskEntity = taskFromOrg;
            }
        }

        if (taskEntity == null) {
            throw new NotFoundException("Can't find task from organization");
        }

        organizationEntity.setSalvations(organizationEntity.getSalvations() + taskDto.getSalvations());

        taskEntity.setAddress(taskDto.getAddress());
        taskEntity.setCity(taskDto.getCity());
        taskEntity.setState(taskDto.getState());
        taskEntity.setId(taskDto.getId());
        taskEntity.setName(taskDto.getName());
        taskEntity.setStatus(taskDto.getStatus());
        taskEntity.setOrg(organizationEntity);
        taskEntity.setTimeSent(taskDto.getTimeSent());
        taskEntity.setSalvations(taskDto.getSalvations());
        taskEntity.setFirstName(taskDto.getFirstName());
        taskEntity.setLastName(taskDto.getLastName());
        taskEntity.setPhoneNumber(taskDto.getPhoneNumber());

        // Add route to complete task, remove from update method
        if (taskEntity.getStatus().equals("complete") && taskDto.getLines() != null) {
            for (LineEntity line: taskEntity.getLines()) {
                line.getCoordinates().removeAll(line.getCoordinates());
            }
           taskDto.getLines().removeAll(taskDto.getLines());
        }
        orgDao.updateTask(taskEntity);


//        if (taskDto.getLines() != null) {
//                List<LineEntity> lineEntities = taskDto.getLines()
//                        .stream()
//                        .map(lineDto -> {
//                            LineEntity lineEntity = new LineEntity();
//                            lineEntity.setId(lineDto.getId());
//                            lineEntity.setTask(finalTaskEntity);
//                            List<CoordinatesEntity> coordinates = lineDto.getCoordinates()
//                                    .stream()
//                                    .map(coordinatesDto -> {
//                                        CoordinatesEntity coordinatesEntity = new CoordinatesEntity();
//                                        coordinatesEntity.setId(coordinatesDto.getId());
//                                        coordinatesEntity.setLat(coordinatesDto.getLat());
//                                        coordinatesEntity.setLng(coordinatesDto.getLng());
//                                        coordinatesEntity.setLine(lineEntity);
//                                        coordinatesEntity.setOrderNumber(coordinatesDto.getOrderNumber());
//                                        return coordinatesEntity;
//                                    })
//                                    .collect(Collectors.toList());
//                            lineEntity.setCoordinates(coordinates);
//                            return lineEntity;
//                        })
//                        .collect(Collectors.toList());
//                taskEntity.setLines(lineEntities);
//            }
    }

    public void createNewLine (LineDto lineDto, String taskId) {
        LineEntity lineEntity = new LineEntity();
        TaskEntity taskEntity = orgDao.getTaskById(taskId);

        List<CoordinatesEntity> coordinatesEntities = lineDto.getCoordinates()
                .stream()
                .map(coordinatesDto -> {
                    CoordinatesEntity coordinatesEntity =  new CoordinatesEntity();
                    coordinatesEntity.setLine(lineEntity);
                    coordinatesEntity.setOrderNumber(coordinatesDto.getOrderNumber());
                    coordinatesEntity.setLat(coordinatesDto.getLat());
                    coordinatesEntity.setLng(coordinatesDto.getLng());
                    return coordinatesEntity;
                })
                .collect(Collectors.toList());

        lineEntity.setTask(taskEntity);
        lineEntity.setCoordinates(coordinatesEntities);

        orgDao.createNewLine(lineEntity);

    }
}
