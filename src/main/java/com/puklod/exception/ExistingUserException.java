package com.puklod.exception;

public class ExistingUserException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ExistingUserException(String ErrorMessage) {
		super(ErrorMessage);
	}
}
