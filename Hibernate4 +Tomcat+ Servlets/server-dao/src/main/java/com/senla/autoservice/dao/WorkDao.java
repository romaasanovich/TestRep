package com.senla.autoservice.dao;

import com.senla.autoservice.api.dao.IWorkDao;
import com.senla.autoservice.bean.Work;
import com.senla.autoservice.dao.abstractdao.GenericDao;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class WorkDao extends GenericDao<Work> implements IWorkDao {

    public WorkDao() {
        super(Work.class);
    }

    public List<Work> getListOfWorks(Session session) throws SQLException {
        Criteria criteria = session.createCriteria(Work.class);
        return  criteria.list();
    }
}
