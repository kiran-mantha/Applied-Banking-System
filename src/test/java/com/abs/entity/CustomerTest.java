package com.abs.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerTest {

	@InjectMocks
	private Customer customer;
	
	@Test
	void test_Object() {
		
		// assigning the values using setter
		customer.setCustomerId(1);
		customer.setCustomerFirstName("Kiran");
		customer.setCustomerLastName("Mantha");
		customer.setCustomerEmail("kiran@gmail.com");
		customer.setCustomerPassword("1234");
		customer.setContact(3569852144l);
		customer.setAddedBy("Kiran");
		customer.setAddedOn(LocalDateTime.now());
		customer.setUpdatedBy("Kiran");
		customer.setUpdatedOn(LocalDateTime.now());
		
		// asserting the values passed into the object
		assertEquals(1, customer.getCustomerId());
		assertEquals("Kiran", customer.getCustomerFirstName());
		assertEquals("Mantha", customer.getCustomerLastName());
		assertEquals("kiran@gmail.com", customer.getCustomerEmail());
		assertEquals("1234", customer.getCustomerPassword());
		assertEquals(3569852144l, customer.getContact());
		assertEquals("Kiran", customer.getAddedBy());
		assertEquals(LocalDateTime.now().getYear(), customer.getAddedOn().getYear());
		assertEquals("Kiran", customer.getUpdatedBy());
		assertEquals(LocalDateTime.now().getMonth(), customer.getUpdatedOn().getMonth());
	}
	
	@Test
	void test_Builder() {
		
		// injecting the values through builder
		customer = Customer.builder()
						   .customerId(1)
						   .customerFirstName("Kiran")
						   .customerLastName("Mantha")
						   .customerEmail("kiran@gmail.com")
						   .customerPassword("1234")
						   .contact(3569852144l)
						   .addedBy("Kiran")
						   .addedOn(LocalDateTime.now())
						   .updatedBy("Kiran")
						   .updatedOn(LocalDateTime.now())
						   .build();
		
		// asserting the values passed into the object
		assertEquals(1, customer.getCustomerId());
		assertEquals("Kiran", customer.getCustomerFirstName());
		assertEquals("Mantha", customer.getCustomerLastName());
		assertEquals("kiran@gmail.com", customer.getCustomerEmail());
		assertEquals("1234", customer.getCustomerPassword());
		assertEquals(3569852144l, customer.getContact());
		assertEquals("Kiran", customer.getAddedBy());
		assertEquals(LocalDateTime.now().getYear(), customer.getAddedOn().getYear());
		assertEquals("Kiran", customer.getUpdatedBy());
		assertEquals(LocalDateTime.now().getMonth(), customer.getUpdatedOn().getMonth());
		
		new Customer.CustomerBuilder().toString();
	}
}
