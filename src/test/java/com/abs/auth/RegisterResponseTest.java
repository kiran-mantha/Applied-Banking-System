package com.abs.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterResponseTest {

	@InjectMocks
	private RegisterResponse regRes;

	private final int ID = 1;
	private final String EMAIL = "kiranmantha@gmail.com";
	private final String PASSWORD = "1234";

	@Test
	void test_Object() {

		regRes = new RegisterResponse();

		regRes.setId(ID);
		regRes.setEmail(EMAIL);
		regRes.setPassword(PASSWORD);

		// Checking the values passed in the object
		assertEquals(ID, regRes.getId());
		assertEquals(EMAIL, regRes.getEmail());
		assertEquals(PASSWORD, regRes.getPassword());
	}

	@Test
	void test_Builder() {

		regRes = RegisterResponse.builder()
								 .id(ID)
								 .email(EMAIL)
								 .password(PASSWORD)
								 .build();

		// Checking the values passed in the object
		assertEquals(ID, regRes.getId());
		assertEquals(EMAIL, regRes.getEmail());
		assertEquals(PASSWORD, regRes.getPassword());
	}

}
