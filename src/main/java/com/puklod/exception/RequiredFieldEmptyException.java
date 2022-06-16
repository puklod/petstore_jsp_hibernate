package com.puklod.exception;

public class RequiredFieldEmptyException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public RequiredFieldEmptyException(String errorMessage) {
		super(errorMessage);
	}

}
