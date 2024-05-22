package com.abs.exception;

public class AccountNotFoundByAccountNumberException extends RuntimeException {

	public AccountNotFoundByAccountNumberException(String message) {
		super(message);
	}
}
