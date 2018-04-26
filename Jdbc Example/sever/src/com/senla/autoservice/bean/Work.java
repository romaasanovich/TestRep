package com.senla.autoservice.bean;

import com.senla.autoservice.api.bean.AEntity;

public class Work extends AEntity {
	private String nameOfService;
	private double price;
	private Master master;

	public Work(Integer id, String nameOfService, double price, Master master) {
		super(id);
		this.nameOfService = nameOfService;
		this.price = price;
		this.setMaster(master);
	}

	public Work(String line) {
		super(0);
		String[] temp = line.split(",");
		setId(Integer.valueOf(temp[1]));
		this.nameOfService = temp[2];
		this.price = Double.valueOf(temp[3]);
	}


	public String getNameOfService() {
		return nameOfService;
	}

	public void setNameOfService(String nameOfService) {
		this.nameOfService = nameOfService;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override

	public String toString() {
		StringBuilder strBuild = new StringBuilder();
		strBuild.append(getId());
		strBuild.append(",");
		strBuild.append(getNameOfService());
		strBuild.append(",");
		strBuild.append(getPrice());
		strBuild.append(",");
		strBuild.append(getMaster().getId());
		return strBuild.toString();
	}

	public Master getMaster() {
		return master;
	}

	public void setMaster(Master master) {
		this.master = master;
	}

}
