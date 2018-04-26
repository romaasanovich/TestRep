package com.senla.autoservice.utills;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import com.senla.autoservice.bean.statusorder.StatusOrder;
import com.senla.autoservice.bean.aentity.AEntity;
import com.senla.autoservice.bean.*;


public class Convert {

    public final static StatusOrder fromStrToStatus(String status) {
        if (status.equals("Broned")) {
            return StatusOrder.Broned;
        } else if (status.equals("Opened")) {
            return StatusOrder.Opened;
        } else if (status.equals("Canceled")) {
            return StatusOrder.Canceled;
        } else if (status.equals("Closed")) {
            return StatusOrder.Closed;
        } else if (status.equals("Deleted")) {
            return StatusOrder.Deleted;
        } else
            return null;
    }

    public final static <T extends AEntity> String[] getEntityStringArray(ArrayList<T> array) {
        String[] response = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            response[i] = array.get(i).toString();
        }
        return response;
    }

    public final static <T extends AEntity> String getEntityStringFromArray(ArrayList<T> array) {
        String message = "";
        for (String str : getEntityStringArray(array)) {
            message += (str + "\n");
        }
        return message;
    }

    public final static Place fromStrToPlace(String line) {
        String[] temp = line.split(",");
        int id = Integer.parseInt(temp[0]);
        boolean isBusy = Boolean.valueOf(temp[2]);
        Place place = new Place(id, temp[1], isBusy);
        return place;
    }

    public final static Work fromStrToWork(String line) {
        String[] temp = line.split(",");
        int id = Integer.parseInt(temp[0]);
        double price = Double.parseDouble(temp[2]);
        int idMaster = Integer.parseInt(temp[3]);
        Master master = new Master(idMaster, null, false);
        Work work = new Work(id, temp[1], price, master);
        return work;
    }

    public final static Master fromStrToMaster(String line) {
        String[] temp = line.split(",");
        int id = Integer.parseInt(temp[0]);
        boolean isWork = Boolean.valueOf(temp[2]);
        Master master = new Master(id, temp[1], isWork);
        return master;
    }

    public final static Order fromStrToOrder(String line) {
        String[] temp = line.split(",");
        int id = Integer.parseInt(temp[0]);
        int idService = Integer.parseInt(temp[1]);
        int idMaster = Integer.parseInt(temp[2]);
        int idPlace = Integer.parseInt(temp[3]);
        StatusOrder st = fromStrToStatus(temp[4]);
        Date ordDate = fromStrToDate(temp[5]);
        Date startDate = fromStrToDate(temp[6]);
        Date endDate = fromStrToDate(temp[7]);
        Work work = new Work(idService, null, 0, null);
        Master master = new Master(idMaster, null, false);
        Place place = new Place(idPlace, null, false);
        Order order = new Order(id, master, work, place, st, ordDate, startDate, endDate);
        return order;
    }

    public final static Date fromStrToDate(String line) {
        String[] tempDate = line.split("-");
        GregorianCalendar grCal = new GregorianCalendar(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]),
                Integer.parseInt(tempDate[2]));
        Date date = (Date) (grCal).getTime();
        return date;
    }

}
