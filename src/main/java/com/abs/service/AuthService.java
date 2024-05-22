package com.abs.service;

import com.abs.auth.AuthenticationRequest;
import com.abs.auth.AuthenticationResponse;
import com.abs.auth.RegisterRequest;
import com.abs.auth.RegisterResponse;
import com.abs.entity.User;
import com.abs.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.BadCredentialsException;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	@Autowired
	private RegisterResponse registerResponse;

	public RegisterResponse register(RegisterRequest registerRequest) {
	
			var user = User.builder()
				.firstName(registerRequest.getFirstName())
				.lastName(registerRequest.getLastName())
				.email(registerRequest.getEmail())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.role(registerRequest.getRole())
				.build();
            User savedUser = userRepository.save(user);
            registerResponse.setId(savedUser.getUserId());
			registerResponse.setEmail(registerRequest.getEmail());
			registerResponse.setPassword(registerRequest.getPassword());

			return registerResponse;
		
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
			String jwtToken = jwtService.generateToken(user);
			
			LOGGER.info("User Authentication Successfull!: {}", user.getFirstName() + " " + user.getLastName());
			return AuthenticationResponse.builder().accessToken(jwtToken).build();
		} catch (BadCredentialsException ex) {
			LOGGER.error("Invalid username or password: {}", ex.getMessage());
			throw new RuntimeException("Invalid username or password", ex);
		} 
	}
}
