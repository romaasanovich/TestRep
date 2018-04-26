package com.senla.autoservice.manager;

import com.senla.autoservice.api.manager.IUserManager;
import com.senla.autoservice.bean.User;
import com.senla.autoservice.dao.UserDao;
import com.senla.autoservice.dao.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserManager implements IUserManager {
    private HibernateUtil hibernateUtil;

    private final UserDao userDao;

    public UserManager() {
        userDao = new UserDao();
        hibernateUtil = HibernateUtil.getInstance();
    }

    @Override
    public void add(final User entity) {
        final Session session;
        Transaction transaction = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            userDao.add(entity, session);
            session.getTransaction().commit();
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public User getById(final int id) {
        final Session session;
        Transaction transaction = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            final User user = userDao.getById(id,session);
            transaction.commit();

            return user;
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return null;
    }

    @Override
    public List<User> getAll(final String column, final String value) {
        final Session session;
        Transaction transaction = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            final List<User> list = userDao.getAll(session, column, value);
            transaction.commit();

            return list;
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return null;
    }

    public User getUser(final String login, final String password){
        final List<User> users = getAll("username", login);

        if (users != null && !users.isEmpty()){
            final User user = users.get(0);

            if (user != null && user.getPassword().equals(password)){
                return user;
            }
        }

        return null;
    }

    public User getUser(final String token){
        final List<User> users = getAll("token", token);

        if (users != null && !users.isEmpty()){
            final User user = users.get(0);

            if (user != null) {
                return user;
            }
        }

        return null;
    }

    public Boolean isValidToken(final String token){
        final List<User> users = getAll("token", token);
        return users != null && !users.isEmpty();
    }
}
