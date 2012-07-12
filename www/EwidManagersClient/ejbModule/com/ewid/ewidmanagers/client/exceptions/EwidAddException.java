package com.ewid.ewidmanagers.client.exceptions;

public class EwidAddException extends RuntimeException{

	private static final long serialVersionUID = 5005002357526913295L;

	public EwidAddException(String cause, Throwable e) {
		super(cause, e);
	}
	
	public EwidAddException(String cause) {
		super(cause);
	}
}
