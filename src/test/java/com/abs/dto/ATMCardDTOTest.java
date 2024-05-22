package com.abs.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ATMCardDTOTest {

	@InjectMocks
	private ATMCardDTO dto;
	
	@Test
	void test_Object() {
		
		dto = new ATMCardDTO();
		
		// Initialising the values through setters
		dto.setCardNumber(1);
		dto.setCardHolder("Kiran");
		dto.setPin(1144);
		dto.setYear(2024);
		dto.setMonth(5);
		dto.setAccount(null);
		
		// asserting the values that are present in the object
		assertEquals(1, dto.getCardNumber());
		assertEquals("Kiran", dto.getCardHolder());
		assertEquals(1144, dto.getPin());
		assertEquals(2024, dto.getYear());
		assertEquals(5, dto.getMonth());
		assertEquals(null, dto.getAccount());
	}
	
	@Test
	void test_Builder() {
		
		// initialising the values through builder
		dto = ATMCardDTO.builder()
				.cardNumber(1)
				.cardHolder("Kiran")
				.pin(1144)
				.year(2024)
				.month(5)
				.account(null)
				.build();
		
		// asserting the values that are present in the object
		assertEquals(1, dto.getCardNumber());
		assertEquals("Kiran", dto.getCardHolder());
		assertEquals(1144, dto.getPin());
		assertEquals(2024, dto.getYear());
		assertEquals(5, dto.getMonth());
		assertEquals(null, dto.getAccount());
		
		new ATMCardDTO.ATMCardDTOBuilder().toString();
	}
}
