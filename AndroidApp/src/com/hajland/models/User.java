package com.hajland.models;

public class User 
{
	private String guid;
	 
    private String owner; // optional field
 
    private boolean conflicted; // optional field
 
    private boolean modified; // optional field
 
    private String id;
 
    private String login;
 
    private String name;
 
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
 
}
