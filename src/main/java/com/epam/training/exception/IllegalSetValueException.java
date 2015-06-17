package com.epam.training.exception;

/* 
 * the exception is thrown when an illegal value is used
 * while setting a parameter of an entity
 */
public class IllegalSetValueException extends Exception {
	private static final long serialVersionUID = -5503506907604173856L;
	private String description;

	/* constructor with a description */
	public IllegalSetValueException(String description) {
		this.description = description;
	}

	@Override
	public String getMessage() {
		return description;
	}
}
