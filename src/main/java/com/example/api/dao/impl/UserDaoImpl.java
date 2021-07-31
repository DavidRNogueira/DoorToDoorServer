package com.example.api.dao.impl;

import com.example.api.dao.UserDao;
import com.example.api.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    public UserDaoImpl (final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public UserEntity getUserByEmail (final String email) {
        Session session = entityManager.unwrap(Session.class);
        List response =  session.createQuery("FROM UserEntity WHERE email =: email ")
                .setParameter("email", email)
                .list();

        UserEntity user = null;

        for (Object o: response) {
            user = (UserEntity) o;
        }

        return user;
    }

    @Transactional
    public UserEntity createNewUser (final UserEntity userEntity) {
        Session session = entityManager.unwrap(Session.class);
        String id = (String) session.save(userEntity);
        return session.get(UserEntity.class, id);
    }

    @Transactional
    public UserEntity getUserById (final String id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(UserEntity.class, id);
    }

    @Transactional
    public void deleteUserById (final String id) {
            Session session = entityManager.unwrap(Session.class);
            Query query = session.createQuery("DELETE FROM UserEntity WHERE id =:id");
            query.setParameter("id", id);
            query.executeUpdate();
        }
}

