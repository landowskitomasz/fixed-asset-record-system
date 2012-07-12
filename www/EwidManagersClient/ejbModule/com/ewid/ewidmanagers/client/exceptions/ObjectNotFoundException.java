package com.ewid.ewidmanagers.client.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -606534717926772589L;

	public ObjectNotFoundException(String cause, Throwable e) {
		super(cause, e);
	}
	
}