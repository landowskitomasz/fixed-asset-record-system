package com.ewid.ewidmanagers.client.struct.mobeelizer.model;

import java.io.Serializable;


public class Equipment implements Serializable {

	private static final long serialVersionUID = 3358885221379509397L;

	private String guid;

    private String owner;

    private boolean conflicted;

    private boolean modified;
    
    private boolean deleted;
    
    private String brand;

    private int id;

    private String model;

    private String serialNumer;
    
    public Equipment() {
        
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumer() {
        return serialNumer;
    }

    public void setSerialNumer(String serialNumer) {
        this.serialNumer = serialNumer;
    }

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Equipment [guid=" + guid + ", owner=" + owner + ", conflicted="
				+ conflicted + ", modified=" + modified + ", deleted="
				+ deleted + ", brand=" + brand + ", id=" + id + ", model="
				+ model + ", serialNumer=" + serialNumer + "]";
	}

}
