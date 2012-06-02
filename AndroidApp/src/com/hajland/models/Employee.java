package com.hajland.models;

import java.util.Date;

public class Employee 
{
	private String guid;
	 
    private String owner; // optional field
 
    private boolean conflicted; // optional field
 
    private boolean modified; // optional field
 
    private Date dateOfBirth;
 
    private String email;
 
    private String name;
 
    private int pesel;
 
    private String placeOfBirth;
 
    private String surname;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPesel() {
		return pesel;
	}

	public void setPesel(int pesel) {
		this.pesel = pesel;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
