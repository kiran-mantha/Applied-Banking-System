package com.abs.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.abs.util.ErrorStructure;

@ExtendWith(MockitoExtension.class)
class ApplicationExceptionHandlerTest {

	@InjectMocks
	private ApplicationExceptionHandler handler;

	@Test
	void test_accountNotFoundByAccountNumberException() {

		String message = "Account Not Found By Given Account Number";

		ResponseEntity<ErrorStructure> entity = handler
				.accountNotFoundByAccountNumberException(new AccountNotFoundByAccountNumberException(message));

		// checking the object is null or not
		assertNotNull(entity.getBody());

		// checking the data is not null
		assertNotNull(entity.getBody().getMessage());

		// Asserting the status code of the Error Structure
		assertEquals(406, entity.getBody().getStatuscode());

		// Checking the message that we sent to the method
		assertEquals(message, entity.getBody().getData());
	}

	@Test
	void test_atmCardNotFoundByNumberException() {

		String message = "ATM Card not Found by the Given Card Number";

		ResponseEntity<ErrorStructure> entity = handler
				.atmCardNotFoundByNumberException(new ATMCardNotFoundByNumberException(message));

		// checking the object is null or not
		assertNotNull(entity.getBody());

		// checking the data is not null
		assertNotNull(entity.getBody().getMessage());

		// Asserting the status code of the Error Structure
		assertEquals(406, entity.getBody().getStatuscode());

		// Checking the message that we sent to the method
		assertEquals(message, entity.getBody().getData());
	}

	@Test
	void test_cardDetailsNotFoundException() {

		String message = "Card is not Found By the Given Details";

		ResponseEntity<ErrorStructure> entity = handler
				.cardDetailsNotFoundException(new CardDetailsNotFoundException(message));

		// checking the object is null or not
		assertNotNull(entity.getBody());

		// checking the data is not null
		assertNotNull(entity.getBody().getMessage());

		// Asserting the status code of the Error Structure
		assertEquals(406, entity.getBody().getStatuscode());

		// Checking the message that we sent to the method
		assertEquals(message, entity.getBody().getData());
	}

	@Test
	void test_incorrectATMPinException() {

		String message = "The PIN for this Card is InCorrect";

		ResponseEntity<ErrorStructure> entity = handler
				.incorrectATMPinException(new IncorrectPinNumberException(message));

		// checking the object is null or not
		assertNotNull(entity.getBody());

		// checking the data is not null
		assertNotNull(entity.getBody().getMessage());

		// Asserting the status code of the Error Structure
		assertEquals(406, entity.getBody().getStatuscode());

		// Checking the message that we sent to the method
		assertEquals(message, entity.getBody().getData());
	}

	@Test
	void test_insufficientBalanceException() {

		String message = "You Do Not Have Sufficient Balance To Perform This Transaction";

		ResponseEntity<ErrorStructure> entity = handler
				.insufficientBalanceException(new InsufficientBalanceException(message));

		// checking the object is null or not
		assertNotNull(entity.getBody());

		// checking the data is not null
		assertNotNull(entity.getBody().getMessage());

		// Asserting the status code of the Error Structure
		assertEquals(406, entity.getBody().getStatuscode());

		// Checking the message that we sent to the method
		assertEquals(message, entity.getBody().getData());
	}

}
