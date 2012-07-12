package com.ewid.ewidmanagers.client.exceptions;

public class EwidDeleteException extends RuntimeException{

	private static final long serialVersionUID = 5005002357526913295L;

	public EwidDeleteException(String cause, Throwable e) {
		super(cause, e);
	}
	
	public EwidDeleteException(String cause) {
		super(cause);
	}
}
