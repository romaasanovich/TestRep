package com.senla.autoservice.facade;

import com.senla.autoservice.bean.*;
import com.senla.autoservice.bean.statusorder.StatusOrder;
import com.senla.autoservice.manager.*;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;
import com.senla.autoservice.bean.User;
import com.senla.autoservice.utills.constants.Constants;
import com.senla.autoservice.utills.hashgenerator.TokenGenerator;
import com.sun.org.apache.bcel.internal.generic.FADD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.FaultAction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Autoservice {

    private GarageManager garageManager;
    private MasterManager masterManager;
    private OrderManager orderManager;
    private WorkManager workManager;
    private UserManager userManager;
    private AuditLogManager auditLogManager;
    private final Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private static Autoservice instance;

    private Autoservice() {
        new Prop();
        orderManager = new OrderManager();
        garageManager = new GarageManager();
        masterManager = new MasterManager();
        workManager = new WorkManager();
        userManager = new UserManager();
        auditLogManager = new AuditLogManager();
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
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return Constants.ERROR;
        }
    }

    public synchronized String addMaster(String name) {
        try {
            String result = "";
            result = masterManager.add(name);
            if (!result.equals("")) {
                return result;
            } else {
                return Constants.ERROR;
            }
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return Constants.ERROR;
        }

    }

    public synchronized void addUser(String login,String password) {
        try {
            String token = TokenGenerator.getMD5Hash(login+password);
            userManager.add(new User(login,password,token));
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
        }

    }


    public synchronized String addOrderToMaster(int idService, int idMaster, int idPlace, StatusOrder status,
                                                String orderDate, String plannedStartDate, String completionDate) {
        Date order = Convert.fromStrToDate(orderDate);
        Date start = Convert.fromStrToDate(plannedStartDate);
        Date completion = Convert.fromStrToDate(completionDate);


        try {
            String result = "";
            result = orderManager.add(idService, idMaster, idPlace, status, order, start,
                    completion);
            if (!result.equals("")) {
                return result;
            } else {
                return Constants.ERROR;
            }
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return Constants.ERROR;
        }
    }

    public synchronized String addWorkToMaster(String name, double price, int idMaster) {
        try {
            String result = "";
            result = workManager.add(name, price, idMaster);
            if (!result.equals("")) {
                return result;
            } else {
                return Constants.ERROR;
            }
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return Constants.ERROR;
        }

    }

    public ArrayList<Place> getAllFreePlaces() {
        try {
            return garageManager.getFreePlaces();
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return null;
        }
    }

    public Place getPlaceById(int id) {
        try {
            return garageManager.getById(id);
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return null;
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
        } catch (Exception e) {
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

        } catch (Exception e) {
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

        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return "";
        }

    }

    public ArrayList<Order> getOrdersByDateOfStart() {
        try {
            return orderManager.getSortedOrder("plannedStartDate");
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return null;
        }
    }

    public String getOrdersByPrice() {
        try {
            String result = "";
            result = Convert.getEntityStringFromArray(orderManager.getSortedOrder("service.price"));
            if (!result.equals("")) {
                return result;
            } else {
                return FacadeMessage.NO_ANY_ORDERS;
            }

        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return "";
        }
    }

    public Order getOrderById(int id) {
        try {
            return orderManager.getById(id);
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return null;
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

        } catch (Exception e) {
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

        } catch (Exception e) {
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

        } catch (Exception e) {
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
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return "";
        }
    }

    public String getOrdersForPeriodTime(String fDate, String sDate) {
        Date first = Convert.fromStrToDate(fDate);
        Date second = Convert.fromStrToDate(sDate);
        try {
            String result = "";
            result = Convert.getEntityStringFromArray(orderManager.getOdersForPeriodOfTime(first, second));
            if (!result.equals("")) {
                return result;
            } else {
                return FacadeMessage.NO_ANY_ORDER;
            }
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return "";
        }
    }

    public ArrayList<Master> getMastersByAlpha() {
        try {
            return masterManager.getSortedMasters("nameMaster");
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return null;
        }
    }

    public ArrayList<Work> getWorks() {
        try {
            return workManager.getWorkList();
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return null;
        }
    }

    public Work getWorkById(int id) {
        try {
            return workManager.getById(id);
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return null;
        }
    }

    public Master getMasterCarriedOutOrder(int idOrder) {
        try {
            return (Master) orderManager.getMasterCarriedOutCurrentOrder(idOrder);
        } catch (Exception e) {
            logger.error(FacadeMessage.LOGGER_MSG, e);
            return null;
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
            } catch (Exception e) {
                logger.error(FacadeMessage.LOGGER_MSG, e);
                return "";
            }
        } else
            return FacadeMessage.UNV_FUNCTION;

    }

    public String exit() {
        return FacadeMessage.EXIT;
    }

    public synchronized String exportMasterCSV() {

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
        } catch (final Exception ignored) {
        }
        return message;

    }

    public synchronized String exportPlaceCSV() {

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
        } catch (final Exception ignored) {
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
        } catch (final Exception ignored) {
        }
        return message;

    }

    public synchronized String importMasterCSV() {

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
        } catch (final Exception ignored) {
        }
        return message;

    }

    public synchronized String importPlaceCSV() {

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
        } catch (final Exception ignored) {
        }
        return message;

    }

    public synchronized String importOrderCSV() {

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
        } catch (final Exception ignored) {
        }
        return message;
    }

    public boolean isValidToken(String token) {
        return userManager.isValidToken(token);
    }

    public void auditRequest(User user, String url,String method) {
        String message = FacadeMessage.AUDIT_TEXT_1+user.getUsername()+ FacadeMessage.AUDIT_TEXT_2+method;
        auditLogManager.add(new AuditLog(user, url,message));
    }



}
