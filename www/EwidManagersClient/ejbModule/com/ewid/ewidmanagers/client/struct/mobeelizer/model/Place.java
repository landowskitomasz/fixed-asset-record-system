package com.ewid.ewidmanagers.client.struct.mobeelizer.model;

import java.io.Serializable;


public class Place implements Serializable {

	private static final long serialVersionUID = 280937303024247600L;

	private String guid;

    private String owner;

    private boolean conflicted;

    private boolean modified;
    
    private boolean deleted;
    
    private String building;

    private String city;

    private String country;

    private int floor;

    private int id;

    private String postalCode;

    private String province;

    private String roomNumber;

    private String street;
    
    public Place() {
        
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(final String guid) {
        this.guid = guid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public boolean isConflicted() {
        return conflicted;
    }

    public void setConflicted(final boolean conflicted) {
        this.conflicted = conflicted;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(final boolean modified) {
        this.modified = modified;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Place [guid=" + guid + ", owner=" + owner + ", conflicted="
				+ conflicted + ", modified=" + modified + ", deleted="
				+ deleted + ", building=" + building + ", city=" + city
				+ ", country=" + country + ", floor=" + floor + ", id=" + id
				+ ", postalCode=" + postalCode + ", province=" + province
				+ ", roomNumber=" + roomNumber + ", street=" + street + "]";
	}

}
