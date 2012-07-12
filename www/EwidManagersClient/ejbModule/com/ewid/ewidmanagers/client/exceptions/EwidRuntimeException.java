package com.ewid.ewidmanagers.client.exceptions;

public class EwidRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 3476094468338498158L;

	public EwidRuntimeException(String cause, Throwable e) {
		super(cause, e);
	}
	
}
