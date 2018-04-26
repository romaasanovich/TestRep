package com.senla.autoservice.manager;


import com.senla.autoservice.api.manager.IAuditLogManager;
import com.senla.autoservice.bean.AuditLog;
import com.senla.autoservice.dao.AuditLogDao;
import com.senla.autoservice.dao.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class AuditLogManager implements IAuditLogManager {

    private HibernateUtil hibernateUtil;

    private final AuditLogDao auditLogDao;

    public AuditLogManager() {
        auditLogDao = new AuditLogDao();
        hibernateUtil = HibernateUtil.getInstance();
    }

    @Override
    public void add(final AuditLog entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            auditLogDao.add(entity, session);
            session.getTransaction().commit();
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public AuditLog get(final int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            final AuditLog log = auditLogDao.getById(id, session);
            transaction.commit();

            return log;
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return null;
    }

    @Override
    public List<AuditLog> getAll(final String column, final String value) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            final List<AuditLog> list = auditLogDao.getAll(session, column, value);
            transaction.commit();

            return list;
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }
}