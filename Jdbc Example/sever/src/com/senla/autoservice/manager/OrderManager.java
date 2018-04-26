package com.senla.autoservice.manager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.senla.autoservice.DBConnector.DBConnector;
import com.senla.autoservice.api.StatusOrder;
import com.senla.autoservice.api.manager.IOrderManager;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.bean.Order;
import com.senla.autoservice.bean.Place;
import com.senla.autoservice.bean.Work;
import com.senla.autoservice.csvimporexport.CsvExportImport;
import com.senla.autoservice.dao.OrderDao;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;

public class OrderManager implements IOrderManager {
	public static final String ADD_ORDER = "INSERT INTO `mydb`.`order` (`idService`, `idMaster`, `idPlace`, `status`, `orderDate`, `plannedStartDate`, `completionDate`) VALUES (?,?,?,?,?,?,?)";
	public static final String UPDATE_ORDER = "UPDATE `mydb`.`order` SET `idService`=?, `idMaster`=?, `idPlace`=?, `status`=?, `orderDate`=?, `plannedStartDate`=?, `completionDate`=? WHERE `id`='?'";

	private OrderDao orders;
	private DBConnector con;
	private CsvExportImport<Order> importExport;

	CsvExportImport<Order> importerExporterPlaces = new CsvExportImport<Order>();

	public OrderManager() throws SQLException {
		orders = new OrderDao();
		con = DBConnector.getInstance();
	}

	public OrderDao getOrders() {
		return orders;
	}

	public String changeStatusOfOrder(int id, StatusOrder status) throws SQLException {
		con.getConnnection().setAutoCommit(false);
		try {
			if (!status.equals(StatusOrder.Opened)) {
				MasterManager masterMan = new MasterManager();
				masterMan.changeBusying(id);
				GarageManager gMan = new GarageManager();
				gMan.changeBusying(id);
				return orders.changeStatusOfOrder(id, status, con.getConnnection());

			}
		} catch (SQLException e) {
			System.out.println("SQLException. Executing rollback to savepoint...");
			con.getConnnection().rollback();
		} finally {
			con.getConnnection().setAutoCommit(true);
		}
		return null;
	}

	public ArrayList<Order> getSortedOrder(String comp) throws SQLException {
		return orders.getListOfOrders(comp, con.getConnnection());
	}

	public ArrayList<Order> getCurrentOrders(String comp) throws SQLException {
		return orders.getListOfCurrentOrders(comp, con.getConnnection());
	}

	public Order getOrderCarriedOutCurrentMaster(int id) throws NullPointerException, SQLException {
		return orders.getOrderCurrentMaster(id, con.getConnnection());
	}

	public ArrayList<Order> getOdersForPeriodOfTime(String fDate, String sDate) throws SQLException {
		return orders.getOdersForPeriodOfTime(fDate, sDate, con.getConnnection());
	}

	public String getCountOfFreePlacesOnDate(String date) throws NullPointerException, SQLException {
		String s = "Count:" + String.valueOf(orders.getCountOfFreePlacesOnDate(date, con.getConnnection()));
		return s;
	}

	public String cloneOrder(int id) throws SQLException {
		return orders.cloneOrder(id, con.getConnnection());
	}

	public String add(int idService, int idMaster, int idPlace, StatusOrder status, String orderDate,
			String plannedStartDate, String completionDate) throws SQLException {
		Master master = new MasterManager().getMasterDao().getById(idMaster, con.getConnnection());
		Place place = new GarageManager().getPlaceDao().getById(idPlace, con.getConnnection());
		Work work = new WorkManager().getWorks().getById(idService, con.getConnnection());
		Date dateOfOrder = Convert.fromStrToDate(orderDate);
		Date startDate = Convert.fromStrToDate(orderDate);
		Date complDate = Convert.fromStrToDate(orderDate);
		Order order = new Order(0, master, work, place, status, dateOfOrder, startDate, complDate);
		return orders.add(order, con.getConnnection());
	}

	private static ArrayList<Order> readFromCSV() throws IOException {
		ArrayList<Order> csvData = new ArrayList<>();
		FileReader fR = new FileReader(new File(Prop.getProp("orderCsvPath")));
		Scanner sc = new Scanner(fR);
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			csvData.add(Convert.fromStrToOrder(s));
		}
		sc.close();
		return csvData;
	}

	@Override
	public void exportFromCSV() throws SQLException, IOException {
		boolean isNew = false;
		PreparedStatement update = (PreparedStatement) con.getConnnection().prepareStatement(UPDATE_ORDER);
		PreparedStatement add = (PreparedStatement) con.getConnnection().prepareStatement(ADD_ORDER);
		ArrayList<Order> dbData = orders.getListOfOrders("id", con.getConnnection());
		ArrayList<Order> csvData = readFromCSV();
		for (Order csvOrder : csvData) {
			isNew = true;
			for (Order sqlWork : dbData) {
				if (csvOrder.getId() == sqlWork.getId()) {
					update.setInt(2, csvOrder.getService().getId());
					update.setInt(3, csvOrder.getMaster().getId());
					update.setInt(4, csvOrder.getPlace().getId());
					update.setString(5, csvOrder.getStatus().toString());
					update.setString(6, Convert.fromDateToStr(csvOrder.getDateOfOrder()));
					update.setString(7, Convert.fromDateToStr(csvOrder.getDateOfPlannedStart()));
					update.setString(8, Convert.fromDateToStr(csvOrder.getDateOfCompletion()));
					update.executeUpdate();
					update.clearParameters();
					isNew = false;
					break;
				}
			}
			if (isNew == true) {
				add.setInt(2, csvOrder.getService().getId());
				add.setInt(3, csvOrder.getMaster().getId());
				add.setInt(4, csvOrder.getPlace().getId());
				add.setString(5, csvOrder.getStatus().toString());
				add.setString(6, Convert.fromDateToStr(csvOrder.getDateOfOrder()));
				add.setString(7, Convert.fromDateToStr(csvOrder.getDateOfPlannedStart()));
				add.setString(8, Convert.fromDateToStr(csvOrder.getDateOfCompletion()));
				add.executeUpdate();
				add.clearParameters();
			}
		}
		update.close();
		add.close();
	}

	@Override
	public void importToCSV() throws SQLException, IOException {
		ArrayList<Order> orderList = orders.getListOfOrders("id", con.getConnnection());
		String path = Prop.getProp("orderCsvPath");
		importExport.importToCsv(orderList, path);
	}

}
