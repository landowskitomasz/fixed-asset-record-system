package com.ewid.ewidmanagers.client.struct;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = -6618225802263789410L;

	private int id;
	private String login;
	private String name;
	private String surname;
	private String password;
	private Date creationDate;
	private boolean active;
	private Group group;
	private boolean synchronizedWithMobeelizer;
	private String guid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
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
		return "User [id=" + id + ", login=" + login + ", name=" + name
				+ ", surname=" + surname + ", password=" + password
				+ ", creationDate=" + creationDate + ", active=" + active
				+ ", group=" + group + ", synchronizedWithMobeelizer="
				+ synchronizedWithMobeelizer + ", guid=" + guid + "]";
	}
	
}
