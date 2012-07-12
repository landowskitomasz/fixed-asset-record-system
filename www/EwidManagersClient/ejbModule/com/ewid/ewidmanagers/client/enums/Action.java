package com.ewid.ewidmanagers.client.enums;

public enum Action {
	ADD(1),
	EDIT(2),
	DELETE(3);
	
	public static Action fromId(int id) {
		for(Action action : Action.values()) {
			if(action.id == id) {
				return action;
			}
		}
		return null;
	}
	
	private Action(int id) {
		this.id = id;
	}
	
	private int id;

	public int getId() {
		return id;
	}
}
