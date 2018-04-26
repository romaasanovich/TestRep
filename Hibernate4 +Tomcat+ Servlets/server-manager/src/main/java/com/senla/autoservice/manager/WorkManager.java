package com.senla.autoservice.manager;

import com.senla.autoservice.api.manager.IWorkManager;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.bean.Work;
import com.senla.autoservice.csvimportexport.CsvExportImport;
import com.senla.autoservice.dao.WorkDao;
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
import java.util.Scanner;

public class WorkManager implements IWorkManager {

    private static final String WORK_WAS_SUCCESFUL_ADDED = "Work was succesful added";

    private CsvExportImport<Work> importExport;
    private WorkDao works;
    private SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();


    public WorkManager() {
        works = new WorkDao();
    }

    public WorkDao getWorks() {
        return works;
    }

    public Work getById(int id) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            return works.getById(id, session);
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }

    public ArrayList<Work> getWorkList() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            ArrayList<Work> work = (ArrayList<Work>) works.getListOfWorks(session);
            return work;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return null;
        }
    }

    public String add(String name, double price, int idMaster) throws Exception {
        String message;
        Master master = new MasterManager().getById(idMaster);
        Work work = new Work(null, name, price, master);
        message = WORK_WAS_SUCCESFUL_ADDED;
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            works.add(work, session);
            tr.commit();
            return message;
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
            return  Constants.ERROR;
        }
    }

    private static ArrayList<Work> readFromCSV() throws IOException {
        ArrayList<Work> csvData = new ArrayList<Work>();
        FileReader fR = new FileReader(new File(Prop.getProp("workCsvPath")));
        Scanner sc = new Scanner(fR);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            csvData.add(Convert.fromStrToWork(s));
        }
        sc.close();
        return csvData;
    }

    public void exportFromCSV() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = null;
        try {
            ArrayList<Work> workCSV = readFromCSV();
            tr = session.beginTransaction();
            for (Work work : workCSV) {
                session.saveOrUpdate(work);
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
            ArrayList<Work> workList = (ArrayList<Work>) works.getListOfWorks(session);
            String path = Prop.getProp("workCsvPath");
            importExport.importToCsv(workList, path);
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
                throw new Exception("Error!!!");
            }
        }
    }

}
