package com.senla.autoservice.dao.abstractdao;

import com.senla.autoservice.bean.aentity.AEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public abstract class GenericDao<T extends AEntity> {
    Class<T> clazz;


    public GenericDao(Class<T> clazz) {
        this.clazz = clazz;
    }


    public T getById(int id, Session session) {
        return (T) session.createCriteria(clazz).add(Restrictions.eq("id", id)).list().get(0);

    }

    public void add(T entity, Session session) {
        session.save(entity);
    }

    public List<T> getAll(final Session session, final String column, final String value) {
        final Criteria criteria = session.createCriteria(clazz);

        if (column != null && value != null){
            criteria.add(Restrictions.eq(column, value));
        }
        return (List<T>) criteria.list();
    }
}
