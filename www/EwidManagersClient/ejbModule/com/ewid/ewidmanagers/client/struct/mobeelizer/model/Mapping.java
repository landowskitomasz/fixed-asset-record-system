package com.ewid.ewidmanagers.client.struct.mobeelizer.model;

import java.io.Serializable;
import java.util.Date;


public class Mapping implements Serializable {

	private static final long serialVersionUID = 1651901581455546806L;

	private String guid;

    private String owner;

    private boolean conflicted;

    private boolean modified;
    
    private boolean deleted;
    
    private int createdBy;

    private Date creationDate;

    private String employee;

    private String equipment;

    private String place;
    
    public Mapping() {
        
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Mapping [guid=" + guid + ", owner=" + owner + ", conflicted="
				+ conflicted + ", modified=" + modified + ", deleted="
				+ deleted + ", createdBy=" + createdBy + ", creationDate="
				+ creationDate + ", employee=" + employee + ", equipment="
				+ equipment + ", place=" + place + "]";
	}

}
