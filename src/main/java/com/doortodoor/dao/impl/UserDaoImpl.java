package com.doortodoor.dao.impl;

import com.doortodoor.dao.UserDao;
import com.doortodoor.dao.bean.OrganizationDaoBean;
import com.doortodoor.dao.bean.UserDaoBean;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserDaoImpl implements UserDao {
    EntityManager entityManager;

    @Inject
    public UserDaoImpl (final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public UserDaoBean getUserByEmail (final String email) {
        Session session = entityManager.unwrap(Session.class);
        List response =  session.createQuery("FROM UserDaoBean WHERE email =: email ")
                .setParameter("email", email)
                .list();

        UserDaoBean user = null;

        for (Object o: response) {
            user = (UserDaoBean) o;
        }
        return user;
    }

    @Transactional
    public UserDaoBean getUserById (final UUID id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(UserDaoBean.class , id);
    }

    @Transactional
    public UUID createUser (final UserDaoBean userDaoBean) {
        Session session = entityManager.unwrap(Session.class);
        return (UUID) session.save(userDaoBean);
    }
}
