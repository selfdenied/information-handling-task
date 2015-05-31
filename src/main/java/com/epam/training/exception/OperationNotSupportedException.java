package com.epam.training.exception;

/* 
 * the exception is thrown when the operation is
 * is not supported for the element
 */
public class OperationNotSupportedException extends Exception {
	private static final long serialVersionUID = 1L;
	private String description;

	/* constructor with a description */
	public OperationNotSupportedException(String description) {
		this.description = description;
	}

	@Override
	public String getMessage() {
		return description;
	}
}
