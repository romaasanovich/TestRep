package com.senla.autoservice.facade;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.autoservice.DBConnector.DBConnector;
import com.senla.autoservice.api.StatusOrder;
import com.senla.autoservice.api.manager.IGarageManager;
import com.senla.autoservice.api.manager.IMasterManager;
import com.senla.autoservice.api.manager.IOrderManager;
import com.senla.autoservice.api.manager.IWorkManager;
import com.senla.autoservice.manager.GarageManager;
import com.senla.autoservice.manager.MasterManager;
import com.senla.autoservice.manager.OrderManager;
import com.senla.autoservice.manager.WorkManager;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;
import com.senla.autoservice.utills.DependencyInjector;
import com.senla.autoservice.utills.constants.Constants;

public class Autoservice {

	private IGarageManager garageManager;
	private IMasterManager masterManager;
	private IOrderManager orderManager;
	private IWorkManager workManager;
	private final Logger logger =  LogManager.getLogger(getClass().getSimpleName());
	DBConnector sqlConnection;

	private static Autoservice instance;

	private Autoservice() {
		new Prop();
		orderManager = DependencyInjector.getInstance(OrderManager.class);
		garageManager = DependencyInjector.getInstance(GarageManager.class);
		masterManager = DependencyInjector.getInstance(MasterManager.class);
		workManager = DependencyInjector.getInstance(WorkManager.class);
	}

	public static Autoservice getInstance() {
		if (instance == null) {
			instance = new Autoservice();
		}
		return instance;
	}

	public synchronized String addPlace(String name) {
		try {
			String result = "";
			result = garageManager.add(name);
			if (!result.equals("")) {
				return result;
			} else
				return Constants.ERROR;
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return Constants.ERROR;
		}
	}

	public synchronized String addMaster(String name) throws SQLException {
		try {
			String result = "";
			result = masterManager.add(name);
			if (!result.equals("")) {
				return result;
			} else {
				return Constants.ERROR;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return Constants.ERROR;
		}

	}

	
	public synchronized String addOrderToMaster(int idService, int idMaster, int idPlace, StatusOrder status,
			String orderDate, String plannedStartDate, String completionDate) {
		try {
			String result = "";
			result = orderManager.add(idService, idMaster, idPlace, status, orderDate, plannedStartDate,
					completionDate);
			if (!result.equals("")) {
				return result;
			} else {
				return Constants.ERROR;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return Constants.ERROR;
		}
	}

	public synchronized String addWorkToMaster(int idWork, String name, double price, int idMaster) {
		try {
			String result = "";
			result = workManager.add(name, price, idMaster);
			if (!result.equals("")) {
				return result;
			} else {
				return Constants.ERROR;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return Constants.ERROR;
		}

	}

	public String getAllFreePlaces() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray( garageManager.getFreePlaces());
			if (!result.equals("")) {
				return result;
			} else
				return FacadeMessage.NO_ANY_PLACES;
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}
	}

	public String getCountOfFreePlacesOnDate(String date) {
		try {
			String result = "";
			result = orderManager.getCountOfFreePlacesOnDate(date);
			if (!result.equals("")) {
				return result;
			} else
				return FacadeMessage.NO_ANY_PLACES;
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}
	}

	public String getOrdersByOrderDate() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(orderManager.getSortedOrder("orderDate"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDERS;
			}

		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}

	}

	public String getOrdersByDateOfCompletion() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(orderManager.getSortedOrder("completionDate"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDERS;
			}

		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}

	}

	public String getOrdersByDateOfStart() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(orderManager.getSortedOrder("plannedStartDate"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDERS;
			}

		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}

	}

	public String getOrdersByPrice() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(orderManager.getSortedOrder("price"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDERS;
			}

		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}

	}

	public String getCurrentOrdersByDateOfOrder() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(orderManager.getCurrentOrders("orderDate"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDERS;
			}

		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}

	}

	public String getCurrentOrdersByDateOfCompletion() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(orderManager.getCurrentOrders("completionDate"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDERS;
			}

		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}

	}

	public String getCurrentOrdersPrice() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(orderManager.getCurrentOrders("price"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDERS;
			}

		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}

	}

	public String getOrderCarriedOutByMaster(int idMaster) {
		try {
			String result = "";
			result = orderManager.getOrderCarriedOutCurrentMaster(idMaster).toString();
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDER;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}
	}

	public String getOrdersForPeriodTime(String fDate, String sDate) {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(orderManager.getOdersForPeriodOfTime(fDate, sDate));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_ORDER;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}
	}


	public synchronized String cloneOrder(Integer id) {
		try {
			String result = "";
			result = orderManager.cloneOrder(id);
			if (!result.equals("")) {
				return result;
			} else {
				return Constants.ERROR;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return Constants.ERROR;
		}
	}

	public String getMastersByBusying() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(masterManager.getSortedMasters("isWork"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_MASTERS;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}
	}

	public String getMastersByAlpha() {
		try {
			String result = "";
			result = Convert.getEntityStringFromArray(masterManager.getSortedMasters("nameMaster"));
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_MASTERS;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);	
			return "";
		}
	}

	public String getMasterCarriedOutOrder(int idOrder) {
		try {
			String result = "";
			result = masterManager.getMasterCarriedOutCurrentOrder(idOrder).toString();
			if (!result.equals("")) {
				return result;
			} else {
				return FacadeMessage.NO_ANY_MASTERS;
			}
		} catch (SQLException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			return "";
		}
	}

	public synchronized String changeStatus(int id, StatusOrder status) {
		Boolean flag = true;
		if (status == StatusOrder.Deleted) {
			String temp = Prop.getProp("toDeleteOrders");
			flag = Boolean.parseBoolean(temp);
		}
		if (flag) {
			try {
				String result = "";
				result = orderManager.changeStatusOfOrder(id, status);
				if (!result.equals("")) {
					return result;
				} else {
					return FacadeMessage.NO_ANY_MASTERS;
				}
			} catch (SQLException e) {
				logger.error(FacadeMessage.LOGGER_MSG, e);
				return "";
			}
		} else
			return FacadeMessage.UNV_FUNCTION;

	}

	public String exit() {
		return FacadeMessage.EXIT;
	}

	public synchronized String exportMasterCSV() throws Exception {

		String message = "";
		try {
			masterManager.exportFromCSV();
			message = FacadeMessage.SUCCESS;
		} catch (final IOException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.FILE_NOT_FOUND;
		} catch (final NoSuchFieldException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.ERROR_WRONG_FIELD;
		} catch (final IllegalAccessException ignored) {
		}
		return message;

	}

	public synchronized String exportPlaceCSV() throws Exception {

		String message = "";
		try {
			garageManager.exportFromCSV();
			message = FacadeMessage.SUCCESS;
		} catch (final IOException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.FILE_NOT_FOUND;
		} catch (final NoSuchFieldException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.ERROR_WRONG_FIELD;
		} catch (final IllegalAccessException ignored) {
		}
		return message;

	}

	public synchronized String exportOrderCSV() throws Exception {

		String message = "";
		try {
			orderManager.exportFromCSV();
			message = FacadeMessage.SUCCESS;
		} catch (final IOException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.FILE_NOT_FOUND;
		} catch (final NoSuchFieldException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.ERROR_WRONG_FIELD;
		} catch (final IllegalAccessException ignored) {
		}
		return message;

	}

	public synchronized String importMasterCSV() throws Exception {

		String message = "";
		try {
			masterManager.importToCSV();
			message = FacadeMessage.SUCCESS;
		} catch (final IOException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.FILE_NOT_FOUND;
		} catch (final NoSuchFieldException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.ERROR_WRONG_FIELD;
		} catch (final IllegalAccessException ignored) {
		}
		return message;

	}

	public synchronized String importPlaceCSV() throws Exception {

		String message = "";
		try {
			garageManager.importToCSV();
			message = FacadeMessage.SUCCESS;
		} catch (final IOException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.FILE_NOT_FOUND;
		} catch (final NoSuchFieldException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.ERROR_WRONG_FIELD;
		} catch (final IllegalAccessException ignored) {
		}
		return message;

	}

	public synchronized String importOrderCSV() throws Exception {

		String message = "";
		try {
			orderManager.importToCSV();
			message = FacadeMessage.SUCCESS;
		} catch (final IOException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.FILE_NOT_FOUND;
		} catch (final NoSuchFieldException e) {
			logger.error(FacadeMessage.LOGGER_MSG, e);
			message = FacadeMessage.ERROR_WRONG_FIELD;
		} catch (final IllegalAccessException ignored) {
		}
		return message;

	}

}
