package com.abs.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abs.enums.Role;

@ExtendWith(MockitoExtension.class)
class RegisterRequestTest {

	@InjectMocks
	private RegisterRequest regReq;
	
	private final String FIRST_NAME = "Kiran";
	private final String LAST_NAME = "Mantha";
	private final String EMAIL = "kiranmantha@gmail.com";
	private final String PASSWORD = "1234";
	private final Role ROLE = Role.MANAGER;
	@Test
	void test_Object() {
		
		regReq = new RegisterRequest();
		
		regReq.setFirstName(FIRST_NAME);
		regReq.setLastName(LAST_NAME);
		regReq.setEmail(EMAIL);
		regReq.setPassword(PASSWORD);
		regReq.setRole(ROLE);
		
		// asserting the data that is passed into the object
		assertEquals(FIRST_NAME, regReq.getFirstName());
		assertEquals(LAST_NAME, regReq.getLastName());
		assertEquals(EMAIL, regReq.getEmail());
		assertEquals(PASSWORD, regReq.getPassword());
		assertEquals(ROLE, regReq.getRole());
	}
	
	@Test
	void test_Builder() {
		
		regReq = RegisterRequest.builder()
					   			.firstName(FIRST_NAME)
					   			.lastName(LAST_NAME)
					   			.email(EMAIL)
					   			.password(PASSWORD)
					   			.role(ROLE)
					   			.build();
		
		// asserting the data that is passed into the object
		assertEquals(FIRST_NAME, regReq.getFirstName());
		assertEquals(LAST_NAME, regReq.getLastName());
		assertEquals(EMAIL, regReq.getEmail());
		assertEquals(PASSWORD, regReq.getPassword());
		assertEquals(ROLE, regReq.getRole());
	}

}
