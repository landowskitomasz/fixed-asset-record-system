package com.ewid.ewidmanagers.client.struct;

import java.io.Serializable;

public class Equipment implements Serializable {

	private static final long serialVersionUID = -2564436297550828525L;
	
	private int id;
	private String brand;
	private String model;
	private String serialNumber;
	private boolean synchronizedWithMobeelizer;
	private String guid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public boolean isSynchronizedWithMobeelizer() {
		return synchronizedWithMobeelizer;
	}
	public void setSynchronizedWithMobeelizer(boolean synchronizedWithMobeelizer) {
		this.synchronizedWithMobeelizer = synchronizedWithMobeelizer;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipment other = (Equipment) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Override
	public String toString() {
		return "Equipment [id=" + id + ", brand=" + brand + ", model=" + model
				+ ", serialNumber=" + serialNumber
				+ ", synchronizedWithMobeelizer=" + synchronizedWithMobeelizer
				+ ", guid=" + guid + "]";
	}
}
