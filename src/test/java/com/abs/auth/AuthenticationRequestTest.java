package com.abs.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationRequestTest {

	@InjectMocks
	private AuthenticationRequest request;
	
	String email = "kiran@gmail.com";
	String password = "1234";
	@Test
	void test_SettersAndGetters() {
		
		request = new AuthenticationRequest();
		
		
		request.setEmail(email);
		request.setPassword(password);
		
		// Checking for the email
		assertEquals(email, request.getEmail());
		
		// Checking for the password
		assertEquals(password, request.getPassword());
	}
	
	@SuppressWarnings("static-access")
	@Test
	void test_Builder() {
		request = AuthenticationRequest.builder()
							 		   .email(email)
							 		   .password(password)
							 		   .build();
		
		// Checking for the email
		assertEquals(email, request.getEmail());
		
		// Checking for the password
		assertEquals(password, request.getPassword());
		
	}
}
