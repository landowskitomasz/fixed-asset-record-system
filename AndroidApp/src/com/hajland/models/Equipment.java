package com.hajland.models;

public class Equipment 
{
	private String guid;
	
	private int id;
	 
    private String owner; // optional field
 
    private boolean conflicted; // optional field
 
    private boolean modified; // optional field
 
    private String brand;
 
    private String model;
 
    private String serialNumer;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isConflicted() {
		return conflicted;
	}

	public void setConflicted(boolean conflicted) {
		this.conflicted = conflicted;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
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

	public String getSerialNumer() {
		return serialNumer;
	}

	public void setSerialNumer(String serialNumer) {
		this.serialNumer = serialNumer;
	}
	
	@Override
	public String toString()
	{
		return this.model + " " + this.brand + "\n\t numer seryjny: " + this.serialNumer;
	}
}
