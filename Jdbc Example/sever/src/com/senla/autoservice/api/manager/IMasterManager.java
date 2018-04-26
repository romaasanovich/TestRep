package com.senla.autoservice.api.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.senla.autoservice.bean.Master;

public interface IMasterManager extends IManager {
	public ArrayList<Master> getSortedMasters(String string) throws SQLException;
	public Master getMasterCarriedOutCurrentOrder(int idOrder) throws SQLException;
	public String add(String name) throws SQLException;
}

