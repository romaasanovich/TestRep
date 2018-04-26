package com.senla.autoservice.dao;

import com.senla.autoservice.api.dao.IGarageDao;
import com.senla.autoservice.bean.Place;
import com.senla.autoservice.dao.abstractdao.GenericDao;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

public class GarageDao extends GenericDao<Place> implements IGarageDao {


    public GarageDao() {
    super(Place.class);
    }

    public void changeBusying(Session session, Place place) throws Exception {
        session.update(place);
    }

    public List<Place> getSortedPlaces(Session session,String comp) throws Exception{
        List<Place> places = session.createCriteria(Place.class).addOrder(Order.asc(comp)).list();
        return places;
    }

    public List<Place> getFreePlaces(Session session) throws SQLException {
        List<Place> places = session.createCriteria(Place.class)
                .add(Restrictions.eq("isBusy", false)).list();
        return places;
    }

}
