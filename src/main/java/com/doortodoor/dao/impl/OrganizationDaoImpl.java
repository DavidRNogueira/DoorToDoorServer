package com.doortodoor.dao.impl;

import com.doortodoor.dao.OrganizationDao;
import com.doortodoor.dao.bean.OrganizationDaoBean;
import com.doortodoor.dao.bean.UserDaoBean;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class OrganizationDaoImpl implements OrganizationDao {
    EntityManager entityManager;

    @Inject
    public OrganizationDaoImpl (final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public OrganizationDaoBean getOrganizationById (final UUID id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(OrganizationDaoBean.class , id);
    }
}
