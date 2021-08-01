package com.example.api.dao;
import com.example.api.entity.LineEntity;
import com.example.api.entity.OrganizationEntity;
import com.example.api.entity.TaskEntity;

import java.util.List;

public interface OrgDao {
    String createTask (TaskEntity taskEntity);
    void deleteTask (String id);
    TaskEntity getTaskById (String id);
    void updateTask (TaskEntity taskEntity);
    void updateListOfTasks (List<TaskEntity> taskEntities);

    void createOrg (OrganizationEntity organizationEntity);
    OrganizationEntity findOrgByOrgUserName (String orgUserName);

    void createNewLine(LineEntity lineEntity);
}
