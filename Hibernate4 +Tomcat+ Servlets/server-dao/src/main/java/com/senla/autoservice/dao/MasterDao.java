package com.senla.autoservice.dao;

import com.senla.autoservice.api.dao.IMasterDao;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.dao.abstractdao.GenericDao;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class MasterDao extends GenericDao<Master> implements IMasterDao {

	public MasterDao() {
		super(Master.class);
	}


	public void changeBusying(Session session,Master master) throws Exception {
		session.update(master);
	}

	public List<Master> getListOfMasters(String comp, Session session) throws Exception {
		List<Master> masters = new ArrayList<Master>();
		masters = session.createCriteria(Master.class).addOrder(org.hibernate.criterion.Order.asc(comp)).list();
		return masters;
	}
}
