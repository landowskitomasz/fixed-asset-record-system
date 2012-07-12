package com.ewid.ewidmanagers.client.struct;

import java.io.Serializable;

public class PurchaseDocument implements Serializable {

	private static final long serialVersionUID = 3233329922435846312L;
	
	private int id;
	private String name;
	
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
	@Override
	public String toString() {
		return "PurchaseDocument [id=" + id + ", name=" + name + "]";
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
		PurchaseDocument other = (PurchaseDocument) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
