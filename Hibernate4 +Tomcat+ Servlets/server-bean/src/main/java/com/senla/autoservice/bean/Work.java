package com.senla.autoservice.bean;

import com.senla.autoservice.bean.aentity.AEntity;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "`work`")
public class Work extends AEntity {
	@Column(name = "nameOfService",length = 45)
	private String nameOfService;
	@Column(name = "price")
	private Double price;
	@ManyToOne
	@JoinColumn(name = "idMaster")
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

	public Work() {
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

	public Master getMaster() {
		return master;
	}

	public void setMaster(Master master) {
		this.master = master;
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


}
