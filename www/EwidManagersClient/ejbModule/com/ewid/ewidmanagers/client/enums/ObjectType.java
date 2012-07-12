package com.ewid.ewidmanagers.client.enums;

public enum ObjectType {
	USER(1),
	EMPLOYEE(2),
	EQUIPMENT(3),
	LOCALIZATION(4),
	MAPPING(5),
	FIXED_ASSET(6);
	
	private int id;
	
	public static ObjectType fromId(int id) {
		for(ObjectType objectType : ObjectType.values()) {
			if(objectType.id == id) {
				return objectType;
			}
		}
		return null;
	}
	
	private ObjectType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
