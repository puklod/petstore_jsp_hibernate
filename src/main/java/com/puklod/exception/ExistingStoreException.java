package com.puklod.exception;

public class ExistingStoreException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ExistingStoreException(String ErrorMessage) {
		super(ErrorMessage);
	}
}
