package com.abs.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerDTOTest {

	@InjectMocks
	private CustomerDTO dto;
	
	@Test
	void test_Object() {
		
		dto = new CustomerDTO();
		
		// Initialising the values through setters
		dto.setCustomerFirstName("Kiran");
		dto.setCustomerLastName("Mantha");
		dto.setCustomerEmail("kiran@gmail.com");
		dto.setCustomerPassword("2583");
		dto.setContact(2558369147l);
		
		// Asserting the values present in the object
		assertEquals("Kiran", dto.getCustomerFirstName());
		assertEquals("Mantha", dto.getCustomerLastName());
		assertEquals("kiran@gmail.com", dto.getCustomerEmail());
		assertEquals("2583", dto.getCustomerPassword());
		assertEquals(2558369147l, dto.getContact());
	}
	
	@Test
	void test_Builder() {
		
		dto = CustomerDTO.builder()
						 .customerFirstName("Kiran")
						 .customerLastName("Mantha")
						 .customerEmail("kiran@gmail.com")
						 .customerPassword("2583")
						 .contact(2558369147l)
						 .build();
		
		// Asserting the values present in the object
		assertEquals("Kiran", dto.getCustomerFirstName());
		assertEquals("Mantha", dto.getCustomerLastName());
		assertEquals("kiran@gmail.com", dto.getCustomerEmail());
		assertEquals("2583", dto.getCustomerPassword());
		assertEquals(2558369147l, dto.getContact());
		
		new CustomerDTO.CustomerDTOBuilder().toString();
	}
}
