package com.ewid.ewidmanagers.client.exceptions;

public class EwidGetObjectException extends RuntimeException{

	private static final long serialVersionUID = 5005002357526913295L;

	public EwidGetObjectException(String cause, Throwable e) {
		super(cause, e);
	}
	
	public EwidGetObjectException(String cause) {
		super(cause);
	}
}
