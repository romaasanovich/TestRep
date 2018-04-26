package com.senla.autoservice.api.manager;

import com.senla.autoservice.bean.Work;
import com.senla.autoservice.bean.aentity.AEntity;
import com.senla.autoservice.api.dao.IWorkDao;

import java.util.ArrayList;

public interface IWorkManager <T extends AEntity> extends IManager{
	public String add(String name,double price, int idMaster) throws Exception;
	public ArrayList<Work> getWorkList() throws Exception;
	public Work getById(int id) throws Exception;
	public IWorkDao getWorks();

}
