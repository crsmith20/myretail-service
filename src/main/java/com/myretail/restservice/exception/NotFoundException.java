package com.myretail.restservice.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1514980243383184854L;

	public NotFoundException(String message) {
		super(message);
	}
}
