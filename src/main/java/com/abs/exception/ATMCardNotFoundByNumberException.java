package com.abs.exception;

public class ATMCardNotFoundByNumberException extends RuntimeException {

	public ATMCardNotFoundByNumberException(String message) {
		super(message);
	}
}
