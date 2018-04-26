package com.senla.autoservice.api.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.senla.autoservice.api.StatusOrder;
import com.senla.autoservice.bean.Order;
import com.senla.autoservice.dao.OrderDao;

public interface IOrderManager extends IManager {

	public OrderDao getOrders();
	public String changeStatusOfOrder(int id, StatusOrder status) throws SQLException;
	public ArrayList<Order> getSortedOrder(String comp) throws SQLException;
	public ArrayList<Order> getCurrentOrders(String comp) throws SQLException;
	public Order getOrderCarriedOutCurrentMaster(int id) throws SQLException;
	public String cloneOrder(int id) throws SQLException;
	public String add(int idService, int idMaster, int idPlace, StatusOrder status, String orderDate,
			String plannedStartDate, String completionDate) throws SQLException;
	public ArrayList<Order> getOdersForPeriodOfTime(String fDate, String sDate)throws SQLException;
	public String getCountOfFreePlacesOnDate(String date) throws NullPointerException, SQLException;
}
