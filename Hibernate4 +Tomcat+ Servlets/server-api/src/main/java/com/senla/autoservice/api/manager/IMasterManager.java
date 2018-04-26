package com.senla.autoservice.api.manager;

import com.senla.autoservice.bean.aentity.AEntity;

import java.util.ArrayList;

public interface IMasterManager <T extends  AEntity>extends IManager {
	public ArrayList<T> getSortedMasters(String string) throws Exception;
	public String add(String name) throws Exception;
}

