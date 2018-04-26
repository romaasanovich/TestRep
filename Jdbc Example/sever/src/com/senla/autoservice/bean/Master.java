package com.senla.autoservice.bean;

import com.senla.autoservice.api.bean.AEntity;
import com.senla.autoservice.utills.Convert;


public class Master extends AEntity {
	private boolean isWork;
	private String name;

	public Master(Integer id, String name, boolean isWork) {
		super(id);
		setName(name);
		this.isWork = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsWork(boolean isWork) {
		this.isWork = isWork;
	}

	public boolean getIsWork() {
		return isWork;
	}

	@Override
	public String toString() {
		String message = getId() + "," + getName() + "," + Convert.fromBooleanToIntSQL(getIsWork());
		return message;
	}
}
