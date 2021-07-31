package com.example.api.dao.impl;

import com.example.api.dao.OrgDao;
import com.example.api.entity.LineEntity;
import com.example.api.entity.OrganizationEntity;
import com.example.api.entity.TaskEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Properties;

@Repository
public class OrgDaoImpl implements OrgDao {
    private EntityManager entityManager;

    public OrgDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public String createTask(final TaskEntity taskEntity) {
        Session session = entityManager.unwrap(Session.class);
        return (String) session.save(taskEntity);
    }

    @Transactional
    public void deleteTask (final String id) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("DELETE FROM TaskEntity WHERE id =: id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public TaskEntity getTaskById (String id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(TaskEntity.class, id);
    }

    @Transactional
    public void updateTask (TaskEntity taskEntity) {
        Session session = entityManager.unwrap(Session.class);
        session.update(taskEntity);
    }

    //Needs testing
    @Transactional
    public void updateListOfTasks (List<TaskEntity> taskEntities) {
        Session session = entityManager.unwrap(Session.class);
        for (TaskEntity taskEntity: taskEntities) {
            session.update(taskEntity);
        }
    }

    @Transactional
    public void createOrg (final OrganizationEntity organizationEntity) {
        Session session = entityManager.unwrap(Session.class);
        session.save(organizationEntity);
    }

    @Transactional
    public OrganizationEntity findOrgByOrgUserName (String orgUserName) {
        Session session = entityManager.unwrap(Session.class);
        List response =  session.createQuery("FROM OrganizationEntity WHERE org_user_name =: orgUserName ")
                .setParameter("orgUserName", orgUserName)
                .list();

        OrganizationEntity organizationEntity = null;

        if (response.size() > 0) {
            for (Object o: response) {
                organizationEntity = (OrganizationEntity) o;
            }
        }

        return organizationEntity;
    }

    @Transactional
    public OrganizationEntity getOrgById (String id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(OrganizationEntity.class, id);
    }

    @Transactional
    public void createNewLine (LineEntity lineEntity) {
        Session session = entityManager.unwrap(Session.class);
        session.save(lineEntity);
    }
}
