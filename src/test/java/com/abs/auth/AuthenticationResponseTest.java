package com.abs.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationResponseTest {

	@InjectMocks
	private AuthenticationResponse authRes;
	
	private final String TOKEN = "access.token.secret.key";
	@Test
	void test_SetterAndGetters() {
		
		authRes = new AuthenticationResponse();
		
		authRes.setAccessToken(TOKEN);
		
		// checking the access token
		assertEquals(TOKEN, authRes.getAccessToken());
	}
	
	@Test
	void test_Builder() {
		
		authRes = AuthenticationResponse.builder()
							  .accessToken("access.token.secret.key")
							  .build();
		
		// checking the access token
		assertEquals(TOKEN, authRes.getAccessToken());
	}

}
