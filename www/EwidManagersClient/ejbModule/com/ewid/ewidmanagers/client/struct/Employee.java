package com.ewid.ewidmanagers.client.struct;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {

	private static final long serialVersionUID = -7899738224793609594L;
	
	private int id;
	private String name;
	private String surname;
	private Date dateOfBirth;
	private String placeOfBirth;
	private String pesel;
	private String email;
	private long msisdn;
	private boolean synchronizedWithMobeelizer;
	private String guid;
	
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getPesel() {
		return pesel;
	}
	public void setPesel(String pesel) {
		this.pesel = pesel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(long msisdn) {
		this.msisdn = msisdn;
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
		Employee other = (Employee) obj;
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
		return "Employee [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", dateOfBirth=" + dateOfBirth + ", placeOfBirth="
				+ placeOfBirth + ", pesel=" + pesel + ", email=" + email
				+ ", msisdn=" + msisdn + ", synchronizedWithMobeelizer="
				+ synchronizedWithMobeelizer + ", guid=" + guid + "]";
	}
	
}
