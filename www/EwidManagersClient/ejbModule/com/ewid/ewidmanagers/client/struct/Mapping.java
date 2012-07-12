package com.ewid.ewidmanagers.client.struct;

import java.io.Serializable;
import java.util.Date;

public class Mapping implements Serializable {

	private static final long serialVersionUID = -6155628932076874448L;
	
	private int id;
	private Equipment equipment;
	private Employee employee;
	private Place place;
	private Date creationDate;
	private User createdBy;
	private Date lastModify;
	private User modifyBy;
	private boolean synchronizedWithMobeelizer;
	private String guid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastModify() {
		return lastModify;
	}
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}
	public User getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(User modifyBy) {
		this.modifyBy = modifyBy;
	}
	public boolean isSynchronizedWithMobeelizer() {
		return synchronizedWithMobeelizer;
	}
	public void setSynchronizedWithMobeelizer(boolean synchronizedWithMobeelizer) {
		this.synchronizedWithMobeelizer = synchronizedWithMobeelizer;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Override
	public String toString() {
		return "Mapping [id=" + id + ", equipment=" + equipment + ", employee="
				+ employee + ", place=" + place + ", creationDate="
				+ creationDate + ", createdBy=" + createdBy + ", lastModify="
				+ lastModify + ", modifyBy=" + modifyBy
				+ ", synchronizedWithMobeelizer=" + synchronizedWithMobeelizer
				+ ", guid=" + guid + "]";
	}
	
}
