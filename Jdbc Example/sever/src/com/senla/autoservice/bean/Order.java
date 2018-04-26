package com.senla.autoservice.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.senla.autoservice.api.StatusOrder;
import com.senla.autoservice.api.bean.AEntity;

public class Order extends AEntity {

	private Master master;
	private Work service;
	private Place place;
	private StatusOrder status;
	private Date dateOfOrder;
	private Date dateOfPlannedStart;
	private Date dateOfCompletion;

	public Order(int id, Master master,  Work service, Place place, StatusOrder status,Date orderDate,Date plannedDate, Date dateOfCompl) {
		super(id);
		this.setMaster(master);
		this.service = service;
		this.place = place;
		setStatus(status);
		dateOfOrder = orderDate;
		dateOfPlannedStart=plannedDate;
		dateOfCompletion = dateOfCompl;
	}

	public Order() {
		super(0);
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Work getService() {
		return service;
	}

	public void setService(Work service) {
		this.service = service;
	}

	public Place getPlace() {
		return place;
	}

	public StatusOrder getStatus() {
		return status;
	}

	public void setStatus(StatusOrder status) {
		this.status = status;
		if (status == StatusOrder.Broned || status == StatusOrder.Deleted || status == StatusOrder.Canceled
				|| status == StatusOrder.Closed) {
			getPlace().setBusy(false);
		} else if (status == StatusOrder.Opened) {
			getPlace().setBusy(true);
		}
	}

	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public Date getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setDateOfCompletion(Date dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public Date getDateOfPlannedStart() {
		return dateOfPlannedStart;
	}

	public void setDateOfPlannedStart(Date dateOfPlannedStart) {
		this.dateOfPlannedStart = dateOfPlannedStart;
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
		strBuild.append(getService().getId());
		strBuild.append(",");
		strBuild.append(getMaster().getId());
		strBuild.append(",");
		strBuild.append(place.getId());
		strBuild.append(",");
		strBuild.append(status.toString());
		strBuild.append(",");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		strBuild.append(dateFormat.format(dateOfOrder));
		strBuild.append(",");
		strBuild.append(dateFormat.format(dateOfPlannedStart));
		strBuild.append(",");
		strBuild.append(dateFormat.format(dateOfCompletion));
		return strBuild.toString();
	}

}
