package com.senla.autoservice.api.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.senla.autoservice.bean.Place;

public interface IGarageManager extends IManager{
	public ArrayList<Place> getFreePlaces() throws SQLException;
	public ArrayList<Place> getSortedPlaces(String name) throws NullPointerException, SQLException;
	public String add(String name) throws SQLException;
}
