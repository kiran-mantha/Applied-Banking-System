package com.abs.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abs.enums.Role;

@ExtendWith(MockitoExtension.class)
class UserTest {

	@InjectMocks
	private User user;
	
	@Test
	void test_Object() {
		
		user = new User();
		
		// injecting the values through setter
		user.setUserId(12);
		user.setFirstName("Kiran");
		user.setLastName("Mantha");
		user.setEmail("kiran@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.MANAGER);
		
		// asserting the values passed to the object
		assertEquals(12, user.getUserId());
		assertEquals("Kiran", user.getFirstName());
		assertEquals("Mantha", user.getLastName());
		assertEquals("kiran@gmail.com", user.getEmail());
		assertEquals("1234", user.getPassword());
		assertEquals(Role.MANAGER, user.getRole());
	}
	
	@Test
	void test_OverridenMethods() {
		
		user.setRole(Role.MANAGER);
		
		// checking the methods are working 
		user.getAuthorities();
		user.getUsername();
		user.isAccountNonExpired();
		user.isAccountNonLocked();
		user.isCredentialsNonExpired();
		user.isEnabled();
	}
	
	@Test
	void test_Builder() {
		
		// injecting the values through builder
		user = User.builder()
				   .userId(12)
				   .firstName("Kiran")
				   .lastName("Mantha")
				   .email("kiran@gmail.com")
				   .password("1234")
				   .role(Role.MANAGER)
				   .build();
		
		// asserting the values passed to the object
		assertEquals(12, user.getUserId());
		assertEquals("Kiran", user.getFirstName());
		assertEquals("Mantha", user.getLastName());
		assertEquals("kiran@gmail.com", user.getEmail());
		assertEquals("1234", user.getPassword());
		assertEquals(Role.MANAGER, user.getRole());
	}
}
