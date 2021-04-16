package com.doortodoor.dao;

import com.doortodoor.dao.bean.TaskDaoBean;

import java.util.List;
import java.util.UUID;

public interface TaskDao {
    UUID createTask (TaskDaoBean taskDaoBean);
    TaskDaoBean getTask (UUID id);
    List<TaskDaoBean> getAllTasks (UUID organizationId);
}
