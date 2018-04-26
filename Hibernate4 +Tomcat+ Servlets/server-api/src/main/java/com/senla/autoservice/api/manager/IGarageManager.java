package com.senla.autoservice.api.manager;

import com.senla.autoservice.bean.Place;
import com.senla.autoservice.bean.aentity.AEntity;

import java.util.ArrayList;

public interface IGarageManager<T extends  AEntity> extends IManager{
	public ArrayList<T> getFreePlaces() throws Exception;
	public ArrayList<T> getSortedPlaces(String name) throws Exception;
	public String add(String name) throws Exception;
	public Place getById(int id) throws Exception ;
}
