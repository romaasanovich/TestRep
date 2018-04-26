package com.senla.autoservice.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.senla.autoservice.api.StatusOrder;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.bean.Order;
import com.senla.autoservice.bean.Place;
import com.senla.autoservice.bean.Work;
import com.senla.autoservice.csvimporexport.CsvExportImport;
import com.senla.autoservice.dao.abstractdao.GenericDao;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;
import com.senla.autoservice.utills.constants.Constants;

public class OrderDao extends GenericDao<Order>{
	
	public static final String ADD_ORDER  = "INSERT INTO `mydb`.`order` (`idService`, `idMaster`, `idPlace`, `status`, `orderDate`, `plannedStartDate`, `completionDate`) VALUES (?,?,?,?,?,?,?)";
	public static final String GET_ORD_FOR_PER_TIME="select mydb.order.id,mydb.master.nameMaster,mydb.order.orderDate,mydb.order.plannedStartDate,mydb.order.completionDate,work.nameOfService,work.price  FROM mydb.order JOIN mydb.work JOIN mydb.master ON mydb.order.idService = mydb.work.id AND mydb.order.idMaster= mydb.master.id AND mydb.order.orderDate > str_to_date(\"?\",'%Y,%m,%d') AND mydb.order.completionDate < str_to_date(\"?\",'%Y,%m,%d')";
	public static final String GET_ORDER_ALL_INFO = "select * FROM mydb.order JOIN mydb.work JOIN mydb.master JOIN mydb.place ON mydb.order.idService = mydb.work.id AND  mydb.order.idPlace = mydb.place.id AND mydb.order.idMaster= mydb.master.id order by";
	public static final String GET_CUR_ORDERS = "select * FROM mydb.order JOIN mydb.work JOIN mydb.master JOIN mydb.place ON mydb.order.idService = mydb.work.id AND  mydb.order.idPlace = mydb.place.id AND mydb.order.idMaster= mydb.master.id AND mydb.order.status=\"Opened\" order by";
	public static final String GET_ORDER_CUR_MASTER= "select * FROM mydb.order JOIN mydb.work JOIN mydb.master JOIN mydb.place ON mydb.order.idService = mydb.work.id AND  mydb.order.idPlace = mydb.place.id AND mydb.order.idMaster= mydb.master.id AND mydb.order.status=\"Opened\" AND mydb.master.id = ";
	public static final String GET_COUNT_PLACE = "SELECT count(id) FROM mydb.place";
	public static final String GET_COUNT_OF_ORDERS_ON_DATE = "select count(mydb.order.id)  FROM mydb.order JOIN mydb.work JOIN  mydb.master ON mydb.order.idService = mydb.work.id AND mydb.order.idMaster= mydb.master.id AND mydb.order.plannedStartDate< str_to_date(\"%s\",'%Y,%m,%d') AND mydb.order.completionDate > str_to_date(\"\"%s\",'%Y,%m,%d')";
	public static final String CHANGE_STATUS = "UPDATE `mydb`.`order` SET `status`='?' WHERE `id`='?'; ";
	public static final String  GET_ORDER_BY_ID ="select mydb.order.* FROM mydb.order JOIN mydb.work JOIN  mydb.master ON mydb.order.idService = mydb.work.id AND mydb.order.idMaster= mydb.master.id AND mydb.order.id =";
	

	public OrderDao() {
	}

	public ArrayList<Order> getListOfOrders(String comp, Connection con) throws SQLException {
		ArrayList<Order> orders = new ArrayList<Order>();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(GET_ORDER_ALL_INFO + "(" + comp + ")");
		while (rs.next()) {
			orders.add(parseFromSql(rs));
		}
		st.close();
		return orders;
	}

	public ArrayList<Order> getListOfCurrentOrders(String comp, Connection con) throws SQLException {
		ArrayList<Order> orders = new ArrayList<Order>();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(GET_CUR_ORDERS + "(" + comp + ")");
		while (rs.next()) {
			orders.add(parseFromSql(rs));
		}
		st.close();
		return orders;

	}

	public Order getOrderCurrentMaster(int id, Connection con) throws SQLException {
		Order order = null;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(GET_ORDER_CUR_MASTER + id);
		while (rs.next()) {
			order = parseFromSql(rs);
		}
		st.close();
		return order;
	}

	public ArrayList<Order> getOdersForPeriodOfTime(String fDate, String sDate, Connection con) throws SQLException {
		ArrayList<Order> orders = new ArrayList<Order>();
		PreparedStatement ps = con.prepareStatement(GET_ORD_FOR_PER_TIME);
		ps.setString(1, fDate);
		ps.setString(2, sDate);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			orders.add(parseFromSql(rs));
		}
		ps.close();
		return orders;
	}

	public int getCountOfPlaces(Connection con) throws SQLException {
		int result;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(String.format(GET_COUNT_PLACE));
		result = rs.getInt(1);
		st.close();
		return result;
	}

	public int getCountOfFreePlacesOnDate(String date, Connection con) throws SQLException {
		int result;
		Statement st = con.createStatement();
		result = getCountOfPlaces(con);
		ResultSet rs = st.executeQuery(String.format(GET_COUNT_OF_ORDERS_ON_DATE, date));
		result = result - rs.getInt(1);
		st.close();
		return result;
	}


	public String changeStatusOfOrder(int id, StatusOrder status, Connection con) throws SQLException {
		PreparedStatement ps =  con.prepareStatement(CHANGE_STATUS);
		ps.setString(1, status.toString());
		ps.setInt(2, id);
		ps.executeUpdate();
		ps.close();
		String result = Constants.SUCCESS;
		return result;

	}

	public String cloneOrder(int id, Connection con) throws SQLException {
		Statement st =con.createStatement();
		ResultSet rs = st.executeQuery(String.format(GET_ORDER_BY_ID + id));
		String result = "";
		Order order = parseFromSql(rs);
		result = add(order,con);
		st.close();
		return result;
	}

	@Override
	public PreparedStatement prepareStmtForGetById(int id,Connection con) throws SQLException {
		PreparedStatement  pstmt= con.prepareStatement(GET_BY_ID);
		pstmt.setString(0, "order");
		pstmt.setInt(1, id);	
		return pstmt;
	}

	@Override
	public Order parseFromSql(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		Master master = new Master(rs.getInt("idMaster"), rs.getString("nameMaster"),
				Convert.fromIntToBooleanSQL(rs.getString("isWork")));
		Work work = new Work(rs.getInt("idService"), rs.getString("nameOfService"), rs.getDouble("price"),
				master);
		Place place = new Place(rs.getInt("idPlace"), rs.getString("placeName"),
				Convert.fromIntToBooleanSQL(rs.getString("isBusy")));
		StatusOrder status = Convert.fromStrToStatus(rs.getString("status"));
		Date dateOfOrder = rs.getDate("orderDate");
		Date dateOfPlannedStart = rs.getDate("plannedStartDate");
		Date dateOfCompletion = rs.getDate("completionDate");
		Order order = new Order(id, master, work, place, status, dateOfOrder, dateOfCompletion, dateOfPlannedStart);
		rs.close();
		return order;
	}

	@Override
	public PreparedStatement prepareStmtToAdd(Connection con, Order order) throws SQLException {
		PreparedStatement ps = con.prepareStatement(ADD_ORDER);
		ps.setInt(1, order.getService().getId());
		ps.setInt(2, order.getMaster().getId());
		ps.setInt(3, order.getPlace().getId());
		ps.setString(4, order.getStatus().toString());
		ps.setString(5, Convert.fromDateToStr(order.getDateOfOrder()));
		ps.setString(6, Convert.fromDateToStr(order.getDateOfPlannedStart()));
		ps.setString(7, Convert.fromDateToStr(order.getDateOfCompletion()));
		return ps;
	}

}
