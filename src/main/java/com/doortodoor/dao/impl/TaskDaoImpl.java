package com.doortodoor.dao.impl;

import com.doortodoor.dao.TaskDao;
import com.doortodoor.dao.bean.TaskDaoBean;
import com.doortodoor.dao.bean.UserDaoBean;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TaskDaoImpl implements TaskDao {
    EntityManager entityManager;

    @Inject
    public TaskDaoImpl (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public UUID createTask (final TaskDaoBean taskDaoBean) {
        Session session = entityManager.unwrap(Session.class);
        return (UUID) session.save(taskDaoBean);
    }

    @Transactional
    public TaskDaoBean getTask (final UUID id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(TaskDaoBean.class, id);
    }

    @Transactional
    public List<TaskDaoBean> getAllTasks (final UUID organizationId) {
        Session session = entityManager.unwrap(Session.class);
        List response =  session.createQuery("FROM TaskDaoBean WHERE organizationFk =: organizationFk ")
                .setParameter("organizationFk", organizationId)
                .list();

        List<TaskDaoBean> taskDaoBeanList = new ArrayList<TaskDaoBean>();

        for (Object o: response) {
            taskDaoBeanList.add((TaskDaoBean) o);
        }

        return taskDaoBeanList;
    }

}
