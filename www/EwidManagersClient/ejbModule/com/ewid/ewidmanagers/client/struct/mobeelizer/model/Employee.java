package com.ewid.ewidmanagers.client.struct.mobeelizer.model;

import java.io.Serializable;
import java.util.Date;


public class Employee implements Serializable {

	private static final long serialVersionUID = -8656830717065772378L;

	private String guid;

    private String owner;

    private boolean conflicted;

    private boolean modified;
    
    private boolean deleted;
    
    private Date dateOfBirth;

    private String email;

    private int id;

    private String name;

    private String pesel;

    private String placeOfBirth;

    private String surname;
    
    public Employee() {
        
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Employee [guid=" + guid + ", owner=" + owner + ", conflicted="
				+ conflicted + ", modified=" + modified + ", deleted="
				+ deleted + ", dateOfBirth=" + dateOfBirth + ", email=" + email
				+ ", id=" + id + ", name=" + name + ", pesel=" + pesel
				+ ", placeOfBirth=" + placeOfBirth + ", surname=" + surname
				+ "]";
	}

}
