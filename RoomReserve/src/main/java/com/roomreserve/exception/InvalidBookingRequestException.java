package com.roomreserve.exception;

public class InvalidBookingRequestException extends RuntimeException {

	public InvalidBookingRequestException(String message) {
		super(message);
	}
}
