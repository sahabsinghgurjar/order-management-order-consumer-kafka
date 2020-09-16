package com.sahab.order.consumer.exceptions;

public class DuplicateOrderException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DuplicateOrderException() {
		super("User Already Exist.");
	}

}
