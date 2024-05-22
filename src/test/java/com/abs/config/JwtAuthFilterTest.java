package com.abs.config;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.abs.repository.UserRepository;
import com.abs.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {

	@InjectMocks
	JwtAuthFilter authFilter;
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserDetailsService userDetailsService;
	@Mock
	private JwtService jwtService;
	@Mock
	private FilterChain filterChain;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	
	@Test
	void testUserNotFoundInDatabase() {
	    // Mock HttpServletRequest, HttpServletResponse, FilterChain, and other dependencies

	    // Set up a valid JWT token in the Authorization header
//	    when(request.getHeader("Authorization")).thenReturn("Bearer valid_jwt_token");

	    // Mock jwtService.extractUsername(jwt) to return a valid email
//	    when(jwtService.extractUsername("valid_jwt_token")).thenReturn("test@example.com");

	    // Mock userDetailsService.loadUserByUsername(email) to return null, indicating user not found
//	    when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(null);

	    // Perform filter execution

//	     Verify that filter chain proceeds without authentication
//	    verify(filterChain, times(1)).doFilter(request, response);
	}

}
