package com.abs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abs.util.ErrorStructure;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorStructure> accountNotFoundByAccountNumberException(AccountNotFoundByAccountNumberException e) {
		
		ErrorStructure error = ErrorStructure.builder().statuscode(HttpStatus.NOT_ACCEPTABLE.value())
								.message("Account not Found")
								.data(e.getMessage())
								.build();
		
		return ResponseEntity.ok(error);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> atmCardNotFoundByNumberException(ATMCardNotFoundByNumberException e) {
		
		ErrorStructure error = ErrorStructure.builder().statuscode(HttpStatus.NOT_ACCEPTABLE.value())
				.message("ATM card not Found")
				.data(e.getMessage())
				.build();

		return ResponseEntity.ok(error);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> cardDetailsNotFoundException(CardDetailsNotFoundException e) {
		
		ErrorStructure error = ErrorStructure.builder().statuscode(HttpStatus.NOT_ACCEPTABLE.value())
				.message("ATM card not Found")
				.data(e.getMessage())
				.build();

		return ResponseEntity.ok(error);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> incorrectATMPinException(IncorrectPinNumberException e) {
		
		ErrorStructure error = ErrorStructure.builder().statuscode(HttpStatus.NOT_ACCEPTABLE.value())
				.message("Wrong PIN")
				.data(e.getMessage())
				.build();
		
		return ResponseEntity.ok(error);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> insufficientBalanceException(InsufficientBalanceException e) {
		
		ErrorStructure error = ErrorStructure.builder().statuscode(HttpStatus.NOT_ACCEPTABLE.value())
				.message("Insufficient Balance")
				.data(e.getMessage())
				.build();
		
		return ResponseEntity.ok(error);
	}
}
