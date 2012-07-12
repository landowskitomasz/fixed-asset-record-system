package com.ewid.ewidmanagers.client.struct.mobeelizer.model;

import java.io.Serializable;


public class User implements Serializable {

	private static final long serialVersionUID = 6192248692135516556L;

	private String guid;

    private String owner;

    private boolean conflicted;

    private boolean modified;
    
    private boolean deleted;
    
    private int id;

    private String login;

    private String name;

    private String password;

    private String surname;
    
    public User() {
        
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
		return "User [guid=" + guid + ", owner=" + owner + ", conflicted="
				+ conflicted + ", modified=" + modified + ", deleted="
				+ deleted + ", id=" + id + ", login=" + login + ", name="
				+ name + ", password=" + password + ", surname=" + surname
				+ "]";
	}

}
