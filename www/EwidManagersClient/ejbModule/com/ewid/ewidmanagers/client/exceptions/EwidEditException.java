package com.ewid.ewidmanagers.client.exceptions;

public class EwidEditException extends RuntimeException{

	private static final long serialVersionUID = 5005002357526913295L;

	public EwidEditException(String cause, Throwable e) {
		super(cause, e);
	}
	
	public EwidEditException(String cause) {
		super(cause);
	}
}
