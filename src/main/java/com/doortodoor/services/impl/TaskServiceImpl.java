package com.doortodoor.services.impl;

import com.doortodoor.dao.TaskDao;
import com.doortodoor.dao.bean.CoordinateDaoBean;
import com.doortodoor.dao.bean.TaskDaoBean;
import com.doortodoor.dto.CoordinateDto;
import com.doortodoor.dto.TaskDto;
import com.doortodoor.mapper.CoordinateMapper;
import com.doortodoor.mapper.TaskMapper;
import com.doortodoor.services.TaskService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskServiceImpl implements TaskService {
    TaskDao taskDao;
    TaskMapper taskMapper;
    CoordinateMapper coordinateMapper;

    @Inject
    public TaskServiceImpl (final TaskDao taskDao, final TaskMapper taskMapper, final CoordinateMapper coordinateMapper) {
        this.taskDao = taskDao;
        this.taskMapper = taskMapper;
        this.coordinateMapper = coordinateMapper;
    }

    @Override
    public UUID createTask (TaskDto taskDto) {
        TaskDaoBean taskDaoBean = taskMapper.taskDtoToDaoBean(taskDto);

        for (CoordinateDaoBean coordinateDaoBean: taskDaoBean.getCoordinates()) {
            coordinateDaoBean.setTask(taskDaoBean);
        }

        return taskDao.createTask(taskDaoBean);
    }

    @Override
    public TaskDto getTask (UUID id) {
        TaskDaoBean taskDaoBean = taskDao.getTask(id);

        if (taskDaoBean == null) {
            throw new NotFoundException();
        }

        return taskMapper.taskDaoBeanToDto(taskDaoBean);
    }

    @Override
    public List<TaskDto> getAllTasks (UUID organizationId) {
        List<TaskDaoBean> taskDaoBeanList = taskDao.getAllTasks(organizationId);
        return taskMapper.taskListDaoBeanToDto(taskDaoBeanList);
    }
}
