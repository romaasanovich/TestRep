package com.senla.autoservice.manager;

import com.senla.autoservice.bean.statusorder.StatusOrder;
import com.senla.autoservice.api.manager.IOrderManager;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.bean.Order;
import com.senla.autoservice.bean.Place;
import com.senla.autoservice.bean.Work;
import com.senla.autoservice.csvimportexport.CsvExportImport;
import com.senla.autoservice.dao.OrderDao;
import com.senla.autoservice.dao.hibernate.HibernateUtil;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;
import com.senla.autoservice.utills.constants.Constants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class OrderManager implements IOrderManager {

    private OrderDao orders;
    private CsvExportImport<Order> importExport;
    private SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();


    public OrderManager() {
        orders = new OrderDao();
    }

    public OrderDao getOrders() {
        return orders;
    }

    public String changeStatusOfOrder(int id, StatusOrder status) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            if (!status.equals(StatusOrder.Opened)) {
                MasterManager masterMan = new MasterManager();
                Master master = masterMan.getMasterDao().getById(id, session);
                masterMan.getMasterDao().changeBusying(session, master);
                GarageManager gMan = new GarageManager();
                Place place = orders.getById(id, session).getPlace();
                gMan.getPlaces().changeBusying(session, place);
                Order order = orders.getById(id, session);
                order.setStatus(status);
                orders.changeStatusOfOrder(order, session);
                tr.commit();
                return Constants.SUCCESS;
            }
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
        }
        return Constants.ERROR;
    }

    public Order getById(int id) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Order order = orders.getById(id, session);
            return order;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }


    }

    public ArrayList<Order> getSortedOrder(String comp) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Order> order = (ArrayList<Order>) orders.getListOfOrders(comp, session);
            return order;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }

    public ArrayList<Order> getCurrentOrders(String comp) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Order> order = (ArrayList<Order>) orders.getListOfCurrentOrders(comp, session);
            return order;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }

    public Order getOrderCarriedOutCurrentMaster(int id) throws Exception {
        MasterManager masterManager = new MasterManager();
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Master master = (Master) masterManager.getMasterDao().getById(id, session);
            Order order = orders.getOrderCurrentMaster(master, session);
            return order;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }

    public Master getMasterCarriedOutCurrentOrder(int idOrder) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Order order = orders.getById(idOrder, session);
            Master master = order.getMaster();
            return master;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }

    public ArrayList<Order> getOdersForPeriodOfTime(Date fDate, Date sDate) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Order> order = (ArrayList<Order>) orders.getOdersForPeriodOfTime(fDate, sDate, session);
            return order;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }

    public String getCountOfFreePlacesOnDate(String strdate) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            Date date = Convert.fromStrToDate(strdate);
            tr = session.beginTransaction();
            int countBusyingPlaces = orders.getCountOfPlacesOnDate(date, session);
            int countPlaces = orders.getListOfOrders("id", session).size();
            int result = countPlaces - countBusyingPlaces;
            String s = "Count:" + String.valueOf(result);
            return s;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return  Constants.ERROR;
        }
    }

    public String cloneOrder(int id) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Order order = getById(id);
            orders.add(order, session);
            return Constants.SUCCESS;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return  Constants.ERROR;
        }
    }

    public String add(int idService, int idMaster, int idPlace, StatusOrder status, Date orderDate,
                      Date plannedStartDate, Date completionDate) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            Master master = new MasterManager().getMasterDao().getById(idMaster, session);
            master.setIsWork(true);
            Place place = new GarageManager().getPlaces().getById(idPlace, session);
            place.setIsBusy(true);
            Work work = new WorkManager().getWorks().getById(idService, session);
            Order order = new Order(0, master, work, place, status, orderDate, plannedStartDate, completionDate);
            tr = session.beginTransaction();
            orders.add(order, session);
            tr.commit();
            return Constants.SUCCESS;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return  Constants.ERROR;
        }
    }


    private ArrayList<Order> readFromCSV() throws IOException {
        ArrayList<Order> csvData = new ArrayList<Order>();
        FileReader fR = new FileReader(new File(Prop.getProp("orderCsvPath")));
        Scanner sc = new Scanner(fR);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            csvData.add(Convert.fromStrToOrder(s));
        }
        sc.close();
        return csvData;
    }

    public void exportFromCSV() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            ArrayList<Order> orderCSV = readFromCSV();
            tr = session.beginTransaction();
            for (Order order : orderCSV) {
                session.saveOrUpdate(order);
            }
            tr.commit();
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
        }
    }

    public void importToCSV() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Order> orderList = (ArrayList<Order>) orders.getListOfOrders("id", session);
            String path = Prop.getProp("orderCsvPath");
            importExport.importToCsv(orderList, path);
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
        }
    }

}
