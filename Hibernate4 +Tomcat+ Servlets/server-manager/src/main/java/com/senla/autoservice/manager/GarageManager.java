package com.senla.autoservice.manager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.senla.autoservice.api.manager.IGarageManager;
import com.senla.autoservice.bean.Place;
import com.senla.autoservice.csvimportexport.CsvExportImport;
import com.senla.autoservice.dao.GarageDao;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;
import com.senla.autoservice.dao.hibernate.HibernateUtil;
import com.senla.autoservice.utills.constants.Constants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GarageManager implements IGarageManager {

    private GarageDao places;
    private CsvExportImport<Place> importExport = new CsvExportImport<Place>();
    private SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();


    public GarageManager() {
        places = new GarageDao();
    }


    public GarageDao getPlaces() {
        return places;
    }

    public Place getById(int id) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            return places.getById(id, session);
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }


    public ArrayList<Place> getSortedPlaces(String comp) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Place> place = (ArrayList<Place>) places.getSortedPlaces(session, comp);
            return place;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }

    public ArrayList<Place> getFreePlaces() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Place> place = (ArrayList<Place>) places.getFreePlaces(session);
            return place;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return  null;
        }

    }


    public String add(String name) throws Exception {
        Place place = new Place(null, name);
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            places.add(place, session);
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

    public String changeBusying(int id, Boolean busying) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Place place = places.getById(id, session);
            place.setIsBusy(busying);
            places.changeBusying(session, place);
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

    public void exportFromCSV() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Place> placesCSV = readFromCSV();
            for (Place place : placesCSV) {
                session.saveOrUpdate(place);
            }
            tr.commit();
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
        }
    }

    private ArrayList<Place> readFromCSV() throws IOException {
        ArrayList<Place> csvData = new ArrayList<Place>();
        FileReader fR = new FileReader(new File(Prop.getProp("placeCsvPath")));
        Scanner sc = new Scanner(fR);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            csvData.add(Convert.fromStrToPlace(s));
        }
        sc.close();
        return csvData;
    }

    public void importToCSV() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Place> placeList = (ArrayList<Place>) places.getSortedPlaces(session, "id");
            String path = Prop.getProp("placeCsvPath");
            importExport.importToCsv(placeList, path);
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
        }
    }
}
