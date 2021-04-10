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
        System.out.println(user.getId());

        return new UserDaoBean();
    }

    @Transactional
    public UserDaoBean getUserById (final UUID id) {
        Session session = entityManager.unwrap(Session.class);
        OrganizationDaoBean organizationDaoBean = session.get(OrganizationDaoBean.class , id);

        System.out.println(organizationDaoBean == null || organizationDaoBean.getName() == null ? null : organizationDaoBean.getName());

        return new UserDaoBean();
    }
}
