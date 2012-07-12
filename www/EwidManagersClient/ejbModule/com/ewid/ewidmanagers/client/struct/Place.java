package com.ewid.ewidmanagers.client.struct;

import java.io.Serializable;

public class Place implements Serializable {

	private static final long serialVersionUID = -301020074517787079L;
	
	private int id;
	private String country;
	private String province;
	private String city;
	private String postalCode;
	private String street;
	private String building;
	private int floor;
	private String roomNumber;
	private boolean synchronizedWithMobeelizer;
	private String guid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
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
		Place other = (Place) obj;
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
		return "Place [id=" + id + ", country=" + country + ", province="
				+ province + ", city=" + city + ", postalCode=" + postalCode
				+ ", street=" + street + ", building=" + building + ", floor="
				+ floor + ", roomNumber=" + roomNumber
				+ ", synchronizedWithMobeelizer=" + synchronizedWithMobeelizer
				+ ", guid=" + guid + "]";
	}
	
}
